package game;
import java.util.Timer;
import java.util.TimerTask;

public class Ticks {
	
	Timer statusTicks;
	Timer lifeTicks;
	
	protected Ticks(){	//default constructor for the main game ticks
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
		Game.a.age += (++ticksPassed/60);
		Game.a.hunger--;
		Game.a.happy--;
		Game.a.hygiene--;
		Game.a.sleep--;
		Game.a.testHealth();
		
		//Tests if tama is well looked after and adds points to a variable. Important for score and random events.
		if(Game.a.hunger > 50 && Game.a.happy > 50 && Game.a.hygiene > 50 && Game.a.sleep > 50)
			Game.a.timeSpentHealthy++;
		
		if(Game.a.hygiene < 5) Game.a.timeSpentDirty += 5;
		else if(Game.a.hygiene < 10) Game.a.timeSpentDirty += 2;
		else if(Game.a.hygiene < 20) Game.a.timeSpentDirty++;
		else if(Game.a.hygiene > 80) Game.a.timeSpentDirty--;
		else if(Game.a.hygiene > 90) Game.a.timeSpentDirty -= 3;
		
		if(Game.a.happy < 5) Game.a.timeSpentUnhappy += 5;
		else if(Game.a.happy < 10) Game.a.timeSpentUnhappy += 2;
		else if(Game.a.happy < 20) Game.a.timeSpentUnhappy++;
		else if(Game.a.happy > 80) Game.a.timeSpentUnhappy--;
		else if(Game.a.happy > 90) Game.a.timeSpentUnhappy -= 3;
		
		Game.a.randomEventListener();
	}
	
	//
	//Sleeping, studying and fitness:
	//
	
	protected Ticks(String status){	//constructor for sleeping, studying and fitness
		statusTicks = new Timer();
		switch(status){
		case "study": 
			System.out.println(Game.a.name+" started studying.");
			statusTicks.schedule(new StudyTask(), 0, 1000);
			Game.state = 2;
			break;
		case "fitness": 
			System.out.println(Game.a.name+" started working out.");
			statusTicks.schedule(new FitnessTask(), 0, 1000);
			Game.state = 2;
			break;
		case "sleep":
			System.out.println(Game.a.name+" went to sleep.");
			statusTicks.schedule(new SleepTask(), 0, 1000);
			Game.state = 3;
			break;
		case "sick":
			System.out.println(Game.a.name + " got sick");
			statusTicks.schedule(new SickTask(), 0, 1000);
			Game.state = 5;
		}
	}
	
	class StudyTask extends TimerTask {	//TimerTask for studying
		@Override
		public void run(){
			Game.a.intelExp += 5;
			Game.a.happy -= 0.2;
			Game.a.levelUp();
			System.out.println("Studying");
			if(Game.a.hunger < 20){
				System.out.println("Too hungry");
				endStatus();
			} else if (Game.a.happy < 20){
				System.out.println("Too unhappy");
				endStatus();
			} else if (Game.a.sleep < 20){
				System.out.println("Getting sleepy");
				endStatus();
			}
		}
	}
	
	class FitnessTask extends TimerTask {	//TimerTask for fitness
		@Override
		public void run(){
			Game.a.fitnessExp += 5;
			Game.a.hunger -= 0.1;
			Game.a.hygiene -= 0.1;
			Game.a.sleep -= 0.1;
			Game.a.levelUp();
			System.out.println("Working out");
			if(Game.a.hunger < 20){
				System.out.println("Too hungry");
				endStatus();
			} else if (Game.a.happy < 20) {
				System.out.println("Too unhappy");
				endStatus();
			} else if (Game.a.sleep < 20){
				System.out.println("Getting sleepy");
				endStatus();
			}
		}
	}
	
	class SleepTask extends TimerTask {	//TimerTask for sleep
		@Override
		public void run(){
			Game.a.sleep += 2;
			if(Game.a.sleep >= 100){
				Game.a.sleep = 100;
				wakeUp();
			}
		}
	}
	
	class SickTask extends TimerTask {
		@Override
		public void run(){
			Game.a.sick += 5;
			Game.a.sleep -= 2;
			Game.a.hygiene -= 2;
			Game.a.hunger -= 2;
			if(Game.a.sick >= 100){
				Game.a.death("sick");
			}
		}
	}
	
	protected void endStatus(){	//method to stop studying or fitness
		this.statusTicks.cancel();
		System.out.println(Game.a.name + " has stopped.");
		Game.state = 1;
		return;
	}
	
	protected void wakeUp(){	//method to end sleep status
		this.statusTicks.cancel();
		System.out.println(Game.a.name + " just woke up.");
		Game.state = 1;
		try {Game.play();} catch (IndexOutOfBoundsException ioobe) {}
	}
	
	protected void getBetter(){
		this.statusTicks.cancel();
		System.out.println(Game.a.name + " has recovered.");
		Game.state = 1;
		return;
	}
}