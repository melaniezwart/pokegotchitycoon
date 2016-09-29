package game;
import java.util.Scanner;

public class Game {
	
	static Tama a;	//Automatically creates new tamagotchi
	
	static Ticks timer;
	static Ticks life;
	static Ticks sickTimer;
	
	static Scanner scan = new Scanner(System.in);
	static int state = 1; //State 1 = wake/ready, state 2 = working, state 3 = sleep, state 4 = dead, 5 = sick, 6 = rebellious;
	
	public static void main(String[] args){
		start();
	}
	
	protected static void start(){	//Executed once upon startup
		a = new Tama();
		System.out.println("What will you call your tama?");
		a.setName(scan.nextLine());
		System.out.println("Your creature is called "+a.name);
		life = new Ticks();
		state = 1;
		play();
	}
	
	protected static void play(){	//Main game loop
		while(true){
			
			if(state == 1){	//Initial print depends on current state
				System.out.println("Hunger: "+a.hunger+" Hygiene: "+a.hygiene+" Happy: "+a.happy+" Sleep: "+a.sleep);
				System.out.println("What will you do?");
			} else if (state == 2){
				System.out.println(a.name + " is busy. Stop them?");
			} else if (state == 3){
				System.out.println(a.name + " is sleeping. Wake them up?");
			} else if (state == 5) {
				System.out.println(a.name + " is sick. Give them medicine?");
			}
			
			String inputAction = scan.nextLine();	//Game loop always halts at this scan line

			if(state == 1){	//Actions available depend on tamagotchi state. State 1 is awake, free and alive
				switch(inputAction){
				case "feed":
					a.eatMeal();
					break;
				case "snack":
					a.eatSnack();
					break;
				case "play card":
					new LuckyDraw();
					break;
				case "play rock":
					new RockPaperScissors();
					break;
				case "play guess":
					new WordGuess();
					break;
				case "wash":
					a.wash();
					break;
				case "study":
					if (a.testWilling()){
						timer = new Ticks("study");
						break;
					}
					break;
				case "fitness":
					if (a.testWilling()){
						timer = new Ticks("fitness");
						break;
					}
					break;
				case "sleep":
					timer = new Ticks("sleep");
					break;
				default:
					System.out.println("KIES IETS");
					break;
				}
			} else if (state == 2){	//State 2 is when tama is studying or fitnessing
				if (inputAction.equals("stop")){
					timer.endStatus();
				} else {
					System.out.println("Wat?");
				}
			} else if (state == 3){	//State 3 is sleep
				if (inputAction.equals("wake")){
					timer.wakeUp();
				} else {
					System.out.println("Wat?");
				}
			} else if (state == 4){ //State 4 is eternal death
				choice(inputAction);
			} else if (state == 5){ //State 5 is sick
				if (inputAction.equals("medicine")){
					sickTimer.getBetter();
				} else {
					System.out.println("Wat?");
				}
			}
		}
	}
	
	protected static void restart(){	//Ends the timer.
		life.lifeTicks.cancel();
		System.out.println("Would you like to restart?");
	}
	
	private static void choice(String choice){	//Upon death, the scan input gets sent to this, answering the question "play again?"
		switch(choice){
		case "y": start();
			break;
		case "yes": start();
			break;
		case "n": System.exit(0);
		case "no": System.exit(0);
		default: System.out.println("Try again.");
			break;
		}
	}
}