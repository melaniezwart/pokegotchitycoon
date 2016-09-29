package game;

import java.util.Random;

public class WordGuess {
	String[] wordArr = {"hospital","consider","voyage","handle","tamagotchi","clean","resonant","permissible","depressed","writing","aloof","order","pause","smiling","clothing","horns","pokemon","screw"};
	int guesses = 0;
	
	protected WordGuess(){
		start(pickWord());
	}
	
	protected String pickWord(){
		Random rng = new Random();
		String word = wordArr[rng.nextInt(wordArr.length - 1)];
		return word;
	}
	
	protected void start(String word){
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
			String guess = Game.scan.nextLine();
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
				System.out.println("You successfully guessed the word, it was: " + word + ". Try again? y/n");
				restart();
			}
		}
		}
	}
	
	private void restart(){
		String retry = Game.scan.nextLine();
		switch(retry){
		case "yes":
		case "y":
		case "play":
		case "again": start(pickWord());
			break;
		case "no":
		case "n":
		case "exit":
		case "stop": return;
		default: System.out.println("Command unclear");
			restart();
			break;
	}
	}
}
