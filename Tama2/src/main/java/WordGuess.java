
import java.util.Scanner;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import java.util.Random;

@ManagedBean(name = "wordguess")
public class WordGuess {
	@Inject
	private Tama a;
	
	@Inject
	Game game;
	
	String[] wordArr = {"hospital","consider","voyage","handle","tamagotchi","clean","resonant","permissible","depressed","writing","aloof","order","pause","smiling","clothing","horns","pokemon","screw"};
	int guesses = 0;
	
	Scanner scan = new Scanner(System.in);
	int gameState = 0; //1 is in game, 2 is in retry status
	
	public WordGuess(){
		start(pickWord());
	}
	
	private String pickWord(){
		Random rng = new Random();
		String word = wordArr[rng.nextInt(wordArr.length - 1)];
		return word;
	}
	
	private void start(String word){
		gameState = 1;
		System.out.println("Your word has "+word.length()+" letters.");
		String totalWord = "";
		for(int i = 0 ; i < word.length() ; i++){
			totalWord = totalWord + "_ ";
		}
		System.out.println(totalWord);
		System.out.println("Start guessing by typing a letter.");
		StringBuilder sbWord = new StringBuilder(totalWord);
		
		boolean guessed = false;
		int lettersGuessed = 0;
		
		while(!guessed){
			String guess = scan.nextLine();
			if(guess.isEmpty()){
				System.out.println("Please pick a letter.");
			} else {
				if(word.contains(guess)){
					int amount = 0;
					for(int index = word.indexOf(guess); 
						index >= 0 ; 
						index = word.indexOf(guess, index + 1))
					{
					lettersGuessed++; amount++;
					int charIndex = index * 2;
					sbWord.replace(charIndex, charIndex + 1, guess);
				}
				
				System.out.println("Yes! The word contains the letter '" + guess + "' " + amount + " time(s)!");
				System.out.println(sbWord);
			} else {
				System.out.println("Nope, try again. " + (word.length() - lettersGuessed) + " letters to go.");
				guesses++;
			}
			if(lettersGuessed == word.length()){
				guessed = true;
				raiseHealth();
				System.out.println("You successfully guessed the word, it was: " + word + ". Try again? y/n");
				gameState = 2;
			}
		}
		}
	}
	private void raiseHealth(){
		a.happy += 5;
		if(a.happy > 100) a.happy = 100;
		a.socialExp += 25;
		a.levelUp();
	}
	
	protected void yes(){
		if(gameState == 2) start(pickWord());
	}
	
	protected void no(){
		if(gameState == 2) game.state = 1;
	}
}
