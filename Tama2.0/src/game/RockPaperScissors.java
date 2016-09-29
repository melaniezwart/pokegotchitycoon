package game;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

	int score = 0;
	Scanner scan = new Scanner(System.in);
	
	public RockPaperScissors(){
		play();
	}
	public void play(){
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
			System.out.println("Your score is: "+score+". Try again.");
			raiseHealth();
			play();

		} else if(choice.equalsIgnoreCase("exit")){
			return;
		} else {
			System.out.println("Your choice was invalid, try again");
			play();
		}
	}
	
	public String draw(int num){
		String[] drawOption = {"Rock", "Paper", "Scissors"};
		return drawOption[num];
	}
	
	private void raiseHealth(){
		Game.a.happy += 5;
		if(Game.a.happy > 100) Game.a.happy = 100;
		Game.a.socialExp += 25;
		Game.a.levelUp();
	}
}
