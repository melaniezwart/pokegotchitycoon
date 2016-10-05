
import java.util.Timer;
import java.util.TimerTask;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

//@ManagedBean(name = "ticks")
public class Ticks {
	Tama tama;
	Game game;
	
	Timer statusTicks;
	Timer lifeTicks;
	
	protected Ticks(Tama tama, Game game){	//default constructor for the main game ticks
		this.tama = tama;
		this.game = game;
		lifeTicks = new Timer();
		lifeTicks.schedule(new TickPass(), 0, 5000);
	}
	
	class TickPass extends TimerTask {	//ticks for health
		@Override
		public void run(){
			passTime();
		}
	}
	
	protected void passTime(){	//Constantly updates tama variables
		double ticksPassed = 0;
		tama.age += (++ticksPassed/30);
		tama.hunger--;
		tama.happy--;
		tama.hygiene--;
		tama.sleep--;
		tama.testHealth(game);
		
		if(tama.hygiene < 0) tama.hygiene = 0;
		if(tama.happy < 0) tama.happy = 0;
		
		tama.randomEventListener(game);
	}
	
	//
	//Sleeping, studying and fitness:
	//
	
	protected Ticks(Tama tama, Game game, String status){	//constructor for sleeping, studying and fitness
		this.tama = tama;
		this.game = game;
		statusTicks = new Timer();
		switch(status){
		case "study": 
			System.out.println(tama.name+" started studying.");
			statusTicks.schedule(new StudyTask(), 0, 1000);
			game.state = 2;
			break;
		case "fitness": 
			System.out.println(tama.name+" started working out.");
			statusTicks.schedule(new FitnessTask(), 0, 1000);
			game.state = 2;
			break;
		case "sleep":
			System.out.println(tama.name+" went to sleep.");
			statusTicks.schedule(new SleepTask(), 0, 1000);
			game.state = 3;
			break;
		case "sick":
			System.out.println(tama.name + " got sick");
			statusTicks.schedule(new SickTask(), 0, 1000);
			game.state = 5;
		case "rebel":
			System.out.println(tama.name+" got rebellious.");
			statusTicks.schedule(new RebelTask(), 0, 1000);
			game.state = 6;
			break;
		}
	}
	
	class StudyTask extends TimerTask {	//TimerTask for studying
		@Override
		public void run(){
			tama.intelExp += 5;
			tama.happy -= 0.2;
			tama.levelUp();
			System.out.println("Studying");
			if(tama.hunger < 20){
				System.out.println("Too hungry");
				endStatus();
			} else if (tama.happy < 20){
				System.out.println("Too unhappy");
				endStatus();
			} else if (tama.sleep < 20){
				System.out.println("Getting sleepy");
				endStatus();
			}
		}
	}
	
	class FitnessTask extends TimerTask {	//TimerTask for fitness
		@Override
		public void run(){
			tama.fitnessExp += 5;
			tama.hunger -= 0.1;
			tama.hygiene -= 0.1;
			tama.sleep -= 0.1;
			tama.levelUp();
			System.out.println("Working out");
			if(tama.hunger < 20){
				System.out.println("Too hungry");
				endStatus();
			} else if (tama.happy < 20) {
				System.out.println("Too unhappy");
				endStatus();
			} else if (tama.sleep < 20){
				System.out.println("Getting sleepy");
				endStatus();
			}
		}
	}
	
	class SleepTask extends TimerTask {	//TimerTask for sleep
		@Override
		public void run(){
			tama.sleep += 2;
			if(tama.sleep >= 100){
				tama.sleep = 100;
				wakeUp();
			}
			if(tama.happy < 5){
				wakeUp();
				System.out.println(tama.name + " woke up feeling miserable. Give them some love.");
			} else if (tama.hunger < 10){
				System.out.println(tama.name + " woke up feeling hungry and ready for breakfast.");
			}
		}
	}
	
	class SickTask extends TimerTask {
		@Override
		public void run(){
			tama.sick += 5;
			tama.sleep -= 2;
			tama.hygiene -= 2;
			tama.hunger -= 2;
			if(tama.sick >= 100){
				tama.death(game, "sick");
			}
		}
	}
	
	class RebelTask extends TimerTask {
		@Override
		public void run(){
			tama.rebel += 5;
			tama.happy -= 3;
			if(tama.rebel == 100){
				tama.death(game, "rebel");
			}
		}
	}
	
	protected void endStatus(){	//method to stop studying or fitness
		this.statusTicks.cancel();
		System.out.println(tama.name + " has stopped.");
		game.state = 1;
	}
	
	protected void wakeUp(){	//method to end sleep status
		this.statusTicks.cancel();
		System.out.println(tama.name + " just woke up.");
		game.state = 1;
	}
	
	protected void getBetter(){
		this.statusTicks.cancel();
		System.out.println(tama.name + " has recovered.");
		game.state = 1;
	}
	
	protected void tellOff(){
		this.statusTicks.cancel();
		System.out.println(tama.name + " will behave now.");
		game.state = 1;
	}
}