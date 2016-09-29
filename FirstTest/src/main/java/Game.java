
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "game")
public class Game {
	
	static Tama a = new Tama();	//Automatically creates new tamagotchi
	RockPaperScissors rps;
	LuckyDraw ld;
	WordGuess wg;
	
	static Ticks timer;
	static Ticks life;
	static Ticks sickTimer;
	static Ticks rebelTimer;
	
	String name = a.name;
	
	double hunger = a.hunger;
	double hygiene = a.hygiene;
	double happy = a.happy;
	double sleep = a.sleep;
	
	int intelLvl = a.intelLvl;
	int fitnessLvl = a.fitnessLvl;
	int socialLvl = a.socialLvl;
	double age = a.age;
	
	public String getName(){
		return name;
	}
	
	public int getHunger() {
		return (int)hunger;
	}
	public int getHygiene(){
		return (int)hygiene;
	}
	public int getHappy(){
		return (int)happy;
	}
	public int getSleep(){
		return (int)sleep;
	}
	
	public int getIntel(){
		return intelLvl;
	}
	public int getFitness(){
		return fitnessLvl;
	}
	public int getSocial(){
		return socialLvl;
	}
	public int getAge(){
		return (int)age;
	}
	
	public void startGame(){
		life = new Ticks();
	}

	static int state = 1; //State 1 = wake/ready, state 2 = working, state 3 = sleep, state 4 = dead, 5 = sick, 6 = rebellious, 7 = in Rock/Paper/Scissors game, 8 = in LuckyDraw game, 9 = in WordGuess game;
	
	//
	//Method for each button, in the order of which they appear
	//
	public void eatMeal(){
		if(state == 1) a.eatMeal();
		else System.out.println("Nope. Can't.");
	}
	public void eatSnack(){
		if(state == 1) a.eatSnack();
		else System.out.println("Nope. Can't.");
	}
	public void wash(){
		if(state == 1) a.wash();
		else System.out.println("Nope. Can't.");
	}
	public void attention(){
		if(state == 1) a.getAttention();
		else System.out.println("Nope. Can't.");
	}
	public void sleep(){
		if(state == 1) timer = new Ticks("sleep");
		else System.out.println("Nope. Can't.");
	}
	public void fitness(){
		if(state == 1) 
			if(a.testWilling()) timer = new Ticks("fitness");
			else System.out.println("Don't wanna");
		else System.out.println("Nope. Can't.");
	}
	public void study(){
		if(state == 1) 
			if(a.testWilling()) timer = new Ticks("study");
			else System.out.println("Don't wanna");
		else System.out.println("Nope. Can't.");
	}
	public void playGuess(){
		if(state == 1) wg = new WordGuess();
		else System.out.println("Nope. Can't.");
	}
	public void playCard(){
		if(state == 1) ld = new LuckyDraw();
		else System.out.println("Nope, can't.");
	}
	public void playRPS(){
		if(state == 1) rps = new RockPaperScissors();
		else System.out.println("Nope, can't.");
	}
	public void yes(){
		System.out.println("Yes");
		if(state == 7) rps.yes();
		else if(state == 8) ld.yes();
		else if(state == 9) wg.yes();
	}
	public void no(){
		System.out.println("No");
		if(state == 2) timer.endStatus();
		else if(state == 3) timer.wakeUp();
		else if(state == 5) sickTimer.getBetter();
		else if(state == 7) rps.no();
		else if(state == 8) ld.no();
		else if(state == 9) wg.no();
	}

/*				System.out.println("Hunger: "+a.hunger+" Hygiene: "+a.hygiene+" Happy: "+a.happy+" Sleep: "+a.sleep);
				System.out.println("What will you do?");
				System.out.println(a.name + " is busy. Stop them?");
				System.out.println(a.name + " is sleeping. Wake them up?");
				System.out.println(a.name + " is sick. Give them medicine?");*/
}