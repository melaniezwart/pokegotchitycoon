package game;

import java.util.Random;

public class Tama{
	
	String name = "Tammo";
	double age;
	
	double hunger = 22;
	double happy = 50;
	double hygiene = 50;
	double sleep = 90;
	double sick = 50;
	
	int intelLvl = 1;
	int intelExp = 0;
	int fitnessLvl = 1;
	int fitnessExp = 0;
	int socialLvl = 1;
	int socialExp = 0;
	
	int timeSpentHealthy = 0;
	int score = (timeSpentHealthy * 5) + intelExp + socialExp + fitnessExp;
	
	int timeSpentDirty = 0;
	int timeSpentUnhappy = 0;
	
	protected String setName(String name){
		this.name = name;
		return name;
	}
	
	//
	//Simple methods to take care of the tama
	//
	
	protected void eatMeal(){
		if(this.hunger > 99){
			System.out.println("Hou op.");
		} else {
			System.out.println("Eating a meal");
			this.hunger += 40;
			if(this.hunger > 100){
				this.hunger = 100;
				System.out.println("Vol is vol.");
			}
		}
	}
	
	protected void eatSnack(){
		System.out.println("Eating a snack");
		this.hunger += 5;
		this.happy += 10;
		if(this.hunger > 100) this.hunger = 100;
		if(this.happy > 100) this.happy = 100;
		if(this.happy == 100 && this.hunger == 100) System.out.println("Nou is het wel genoeg");
	}
	
	protected void wash(){
		System.out.println("Taking a bath");
		this.hygiene += 50;
		if(this.hygiene > 100){
			this.hygiene = 100;
			System.out.println("Ik heb er schoon genoeg van");
		}
	}
	
	//
	//Checking the health of the tama and the consequenses
	//
	
	protected void testHealth(){ //On every tick, this gets executed, testing the health and printing text.
		if(this.hunger < 0){
			death("starved");
		} else if(this.hunger <5){
			System.out.println("Time is ticking out, please feed " + this.name + " now.");
		} else if (this.hunger < 10){
			System.out.println(this.name + " is starving. Please feed them.");
		} else if (this.hunger < 20){
			System.out.println(this.name + " is getting hungry. You should feed them soon.");
		}
		if(this.happy <5){
			System.out.println(this.name + " probably won't listen to you anymore until you give them some love and attention."); 
		} else if (this.happy < 10){
			System.out.println(this.name + " really needs some attention.");
		} else if (this.happy < 20){
			System.out.println(this.name + " is getting unhappy. Play with them or give them some love.");
		}
		if(this.hygiene <5){
			System.out.println("It smells like something's dying in here. " + this.name + " could get sick from this."); 
		} else if (this.hygiene < 10){
			System.out.println(this.name + " stinks.");
		} else if (this.hygiene < 20){
			System.out.println(this.name + " is starting to smell.");
		}
		if (Game.state != 3){
			if(this.sleep <5){
				System.out.println(this.name + " could fall asleep any minute now.");
			} else if (this.sleep < 10){
				System.out.println(this.name + " can barely keep their eyes open.");
			} else if (this.sleep < 20){
				System.out.println(this.name + " is getting sleepy.");
			}
		}
	}
	
	protected void death(String cause){	//Prints the cause of death, then some information on the tama. 
		Game.state = 4;
		switch (cause){
		case "starved": 
			System.out.println(this.name + " starved to death. Rest in piece, poor neglected soul. You were too good for this world.");
			break;
		case "sick": 
			System.out.println(this.name + " died of an illness. Poor thing. Maybe you should've taken better care of them. Rest in piece. You deserved better.");
			break;
		}
		System.out.print(this.name + " was " + this.age + " days old. \n"
				+ "Their intelligence level was " + this.intelLvl + "\n"
				+ "Their fitness level was " + this.fitnessLvl+"\n"
				+ "Your total score is: " + this.score + "\n");
		Game.restart();
	}
	
	protected boolean testWilling(){	//Checks if tamagotchi is up for anything. Returns boolean false when one value is too low
		if(this.hunger < 20){
			System.out.println(this.name + " is too hungry for this.");
			return false;
		} else if (this.happy < 20){
			System.out.println(this.name + " is too unhappy for this.");
			return false;
		} else if (this.sleep < 20){
			System.out.println(this.name + " is too sleepy for this.");
			return false;
		} else {
			return true;
		}
	}
	
	//
	//Method for studying, fitness and social levels
	//
	
	protected void levelUp(){ //Tests if the current experience is higher than the level threshhold as shown in the array.
		int[] expArray = {0, 100, 250, 500, 800, 1200, 1750, 2500, 5000, 7500, 10000, 12500, 15000, 17500, 20000};
		if(this.intelExp > expArray[this.intelLvl]){
			this.intelLvl++;
			System.out.println(this.name + " levelled up in Intelligence.");
		}
		if(this.fitnessExp > expArray[this.fitnessLvl]){
			this.fitnessLvl++;
			System.out.println(this.name + " levelled up in Fitness.");
		}
		if(this.socialExp > expArray[this.socialLvl]){
			this.socialLvl++;
			System.out.println(this.name + " levelled up in Social skill.");
		}
	}
	
	//
	//Random event listener
	//
	
	protected void randomEventListener(){
		double rebelScore = (this.timeSpentUnhappy / 1000);
		double sickScore = (this.timeSpentDirty / 1000);
		double happyScore = (this.timeSpentHealthy / 1000);
		
		Random rng = new Random();
		double rebelNumber = (rng.nextInt(100) + rebelScore - happyScore);
		double sickNumber = (rng.nextInt(100) + sickScore);
		
		if(rebelNumber > 110) new RandomEvent("rebel");
		if(sickNumber > 105) Game.sickTimer = new Ticks("sick");

	}
	
	//Obsolete since implementation of 2 minigames.
	/*protected void playGame(){
	System.out.println("Playing a game");
	this.happy += 20;
	if(this.happy > 100) {
		this.happy = 100;
		System.out.println(this.name + " heeft wel wat beters te doen");
	}
}*/
}
