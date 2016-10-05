package game;

import java.util.Timer;
import java.util.TimerTask;

public class Ticks {
	
	Timer statusTicks;
	Timer lifeTicks;
	Tama tama = Game.getMyTama();
	
	protected Ticks(){	//default constructor for the main game ticks
		lifeTicks = new Timer();
		lifeTicks.schedule(new TickPass(), 0, 5000);
	}
	public Ticks(Tama tama){
		this.tama = tama;
		lifeTicks = new Timer();
		lifeTicks.schedule(new TickPass(), 0, 5000);
	}
	
	class TickPass extends TimerTask {	//ticks for health
		@Override
		public void run(){
			tama.passTime();
		}
	}
	
	//
	//Sleeping, studying and fitness:
	//
	
	protected Ticks(String status){	//constructor for sleeping, studying and fitness
		statusTicks = new Timer();
		switch(status){
		case "study": 
			System.out.println(tama.name+" started studying.");
			statusTicks.schedule(new StudyTask(), 0, 1000);
			Game.state = 2;
			break;
		case "fitness": 
			System.out.println(tama.name+" started working out.");
			statusTicks.schedule(new FitnessTask(), 0, 1000);
			Game.state = 2;
			break;
		case "sleep":
			System.out.println(tama.name+" went to sleep.");
			statusTicks.schedule(new SleepTask(), 0, 1000);
			Game.state = 3;
			break;
		case "sick":
			System.out.println(tama.name + " got sick");
			statusTicks.schedule(new SickTask(), 0, 1000);
			Game.state = 5;
		case "rebel":
			System.out.println(tama.name+" got rebellious.");
			statusTicks.schedule(new RebelTask(), 0, 1000);
			Game.state = 6;
			break;
		}
	}
	
	class StudyTask extends TimerTask {	//TimerTask for studying
		@Override
		public void run(){
			tama.studyTask();
		}
	}
	
	class FitnessTask extends TimerTask {	//TimerTask for fitness
		@Override
		public void run(){
			tama.fitnessTask();
		}
	}
	
	class SleepTask extends TimerTask {	//TimerTask for sleep
		@Override
		public void run(){
			tama.sleepTask();
		}
	}
	
	class SickTask extends TimerTask {
		@Override
		public void run(){
			tama.sickTask();
		}
	}
	
	class RebelTask extends TimerTask {
		@Override
		public void run(){
			tama.rebelTask();
		}
	}
	
	protected void endStatus(){	//method to stop studying or fitness
		this.statusTicks.cancel();
		System.out.println(tama.name + " has stopped.");
		Game.state = 1;
	}
	
	protected void wakeUp(){	//method to end sleep status
		this.statusTicks.cancel();
		System.out.println(tama.name + " just woke up.");
		Game.state = 1;
	}
	
	protected void getBetter(){
		this.statusTicks.cancel();
		System.out.println(tama.name + " has recovered.");
		Game.state = 1;
	}
	
	protected void tellOff(){
		this.statusTicks.cancel();
		System.out.println(tama.name + " will behave now.");
		Game.state = 1;
	}
}