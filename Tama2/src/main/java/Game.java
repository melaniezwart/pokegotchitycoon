
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

@ManagedBean(name = "game")
//@SessionScoped
public class Game {
	
//	static Tama a = new Tama();	//Automatically creates new tamagotchi
	@Inject
	private Tama tama;
	
	RockPaperScissors rps;
	LuckyDraw ld;
	WordGuess wg;
	
	static Ticks timer;
	static Ticks life;
	static Ticks sickTimer;
	static Ticks rebelTimer;
	
	//String name = getA().name;
	
	//double hunger = getA().hunger;
	//double hygiene = getA().hygiene;
	//double happy = getA().happy;
	//double sleep = getA().sleep;
	
	//int intelLvl = getA().intelLvl;
	//int fitnessLvl = getA().fitnessLvl;
	//int socialLvl = getA().socialLvl;
	//double age = getA().age;
	
	public String getName(){
		return tama.name;
	}
	
	public int getHunger() {
		return (int)tama.hunger;
	}
	public int getHygiene(){
		return (int)tama.hygiene;
	}
	public int getHappy(){
		return (int)tama.happy;
	}
	public int getSleep(){
		return (int)tama.sleep;
	}
	
	public int getIntel(){
		return tama.intelLvl;
	}
	public int getFitness(){
		return tama.fitnessLvl;
	}
	public int getSocial(){
		return tama.socialLvl;
	}
	public int getAge(){
		return (int)tama.age;
	}
	
	public void startGame(){
		life = new Ticks(tama, this);
	}

	int state = 1; //State 1 = wake/ready, state 2 = working, state 3 = sleep, state 4 = dead, 5 = sick, 6 = rebellious, 7 = in Rock/Paper/Scissors game, 8 = in LuckyDraw game, 9 = in WordGuess game;
	
	//
	//Method for each button, in the order of which they appear
	//
	public void eatMeal(){
		if(state == 1) getTama().eatMeal();
		else System.out.println("Nope. Can't.");
	}
	public void eatSnack(){
		if(state == 1) getTama().eatSnack();
		else System.out.println("Nope. Can't.");
	}
	public void wash(){
		if(state == 1) getTama().wash();
		else System.out.println("Nope. Can't.");
	}
	public void attention(){
		if(state == 1) getTama().getAttention();
		else System.out.println("Nope. Can't.");
	}
	public void sleep(){
		if(state == 1) timer = new Ticks(tama, this, "sleep");
		else System.out.println("Nope. Can't.");
	}
	public void fitness(){
		if(state == 1) 
			if(getTama().testWilling()) timer = new Ticks(tama, this, "fitness");
			else System.out.println("Don't wanna");
		else System.out.println("Nope. Can't.");
	}
	public void study(){
		if(state == 1) 
			if(getTama().testWilling()) timer = new Ticks(tama, this, "study");
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

	public Tama getTama() {
		return tama;
	}

/*				System.out.println("Hunger: "+a.hunger+" Hygiene: "+a.hygiene+" Happy: "+a.happy+" Sleep: "+a.sleep);
				System.out.println("What will you do?");
				System.out.println(a.name + " is busy. Stop them?");
				System.out.println(a.name + " is sleeping. Wake them up?");
				System.out.println(a.name + " is sick. Give them medicine?");*/
}