package game;

import java.util.Scanner;
import java.util.Random;

public class WordGuess {
	String[] wordArr = {"hospital","consider","voyage","handle","tamagotchi","clean","resonant","permissible","depressed","writing","aloof","order","pause","smiling","clothing","horns","pokemon","screw"};
	int guesses = 0;
	
	Scanner scan = new Scanner(System.in);
	int gameState = 0; //1 is in game, 2 is in retry status
	
	public WordGuess(Tama tama){
		start(pickWord(), tama);
	}
	
	private String pickWord(){
		Random rng = new Random();
		String word = wordArr[rng.nextInt(wordArr.length - 1)];
		return word;
	}
	
	private void start(String word, Tama tama){
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
				raiseHealth(tama);
				System.out.println("You successfully guessed the word, it was: " + word + ". Try again? y/n");
				gameState = 2;
			}
		}
		}
	}
	private void raiseHealth(Tama tama){
		tama.happy += 5;
		if(tama.happy > 100) tama.happy = 100;
		tama.socialExp += 25;
		tama.levelUp();
	}
	
	protected void yes(Tama tama){
		if(gameState == 2) start(pickWord(), tama);
	}
	
	protected void no(){
		if(gameState == 2) Game.state = 1;
	}
}
