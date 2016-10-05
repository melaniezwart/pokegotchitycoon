package game;

import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

	int score = 0;
	Scanner scan = new Scanner(System.in);
	int gameState = 0; //1 is in game, 2 is in retry;
	
	public RockPaperScissors(Tama tama){
		play(tama);
	}
	private void play(Tama tama){
		gameState = 1;
		System.out.println("Type 'rock', 'paper', 'scissors', or 'exit'.");
		String choice = scan.nextLine();
		
		if(choice.equalsIgnoreCase("rock") | choice.equalsIgnoreCase("paper") | choice.equalsIgnoreCase("scissors")){
			Random rng = new Random();
			int randomNum = rng.nextInt(3);
			String result = draw(randomNum).toLowerCase();
			System.out.println("Your opponent chose "+result);
			
	outer:	switch(choice){
			case "rock":
				switch(result){
				case "rock":
					System.out.println("It's a draw!");
					break outer;
				case "paper":
					System.out.println("You lose.");
					break outer;
				case "scissors":
					System.out.println("You win!");
					this.score++;
					break outer;
				}
			case "paper":
				switch(result){
				case "rock":
					System.out.println("You win!");
					this.score++;
					break outer;
				case "paper":
					System.out.println("It's a draw!");
					break outer;
				case "scissors":
					System.out.println("You lose.");
					break outer;
				}
			case "scissors":
				switch(result){
				case "rock":
					System.out.println("You lose.");
					break outer;
				case "paper":
					System.out.println("You win!");
					this.score++;
					break outer;
				case "scissors":
					System.out.println("It's a draw!");
					break outer;
				}
			}
			System.out.println("Your score is: "+score+". Try again?");
			raiseHealth(tama);
			gameState = 2;

		}
	}
	
	private String draw(int num){
		String[] drawOption = {"Rock", "Paper", "Scissors"};
		return drawOption[num];
	}
	
	private void raiseHealth(Tama tama){
		tama.happy += 5;
		if(tama.happy > 100) tama.happy = 100;
		tama.socialExp += 25;
		tama.levelUp();
	}
	
	protected void yes(Tama tama){
		if(gameState == 2) play(tama);
	}
	
	protected void no(){
		if(gameState == 2) Game.state = 1;
	}
	
	
}
