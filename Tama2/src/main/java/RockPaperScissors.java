
import java.util.Random;
import java.util.Scanner;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

@ManagedBean(name = "rockpaperscissors")
public class RockPaperScissors {

	@Inject
	private Tama a;
	
	@Inject
	private Game game;
	
	int score = 0;
	Scanner scan = new Scanner(System.in);
	int gameState = 0; //1 is in game, 2 is in retry;
	
	public RockPaperScissors(){
		play();
	}
	private void play(){
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
			raiseHealth();
			gameState = 2;

		}
	}
	
	private String draw(int num){
		String[] drawOption = {"Rock", "Paper", "Scissors"};
		return drawOption[num];
	}
	
	private void raiseHealth(){
		a.happy += 5;
		if(a.getHappy() > 100) a.setHappy(100);
		a.socialExp += 25;
		a.levelUp();
	}
	
	protected void yes(){
		if(gameState == 2) play();
	}
	
	protected void no(){
		if(gameState == 2) game.state = 1;
	}
	
	
}
