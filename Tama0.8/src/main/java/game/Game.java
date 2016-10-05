package game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import db.*;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "game")
@ApplicationScoped
public class Game extends DatabaseConnect{
	
	private static int id = 0;
	private static Tama myTama = new Tama();
	
	public static Tama getMyTama() {
		return myTama;
	}
	public static void setMyTama(Tama myTama) {
		Game.myTama = myTama;
	}

	public void startGame(int id){
		Game.id = id;
		System.out.println("init game");
		loadTama(id);
		System.out.println(id);
		myTama.lifeTimer = new Ticks(myTama);
	}
	
	public static void endGame(){
		myTama.lifeTimer.lifeTicks.cancel();
	}
		
	public int getId() {
		System.out.println("return id " + id);
		return id;
	}

	public void setId(int id) {
		System.out.println("set id " + id +" to "+ Game.id);
		Game.id = id;
	}

	//Tama myTama = new Tama();	//Automatically creates new tamagotchi
	RockPaperScissors rps;
	LuckyDraw ld;
	WordGuess wg;
	
	static Ticks timer;
	Ticks life;
	
	String name = myTama.name;
	
	double hunger = myTama.hunger;
	double hygiene = myTama.hygiene;
	double happy = myTama.happy;
	double sleep = myTama.sleep;
	
	int intelLvl = myTama.intelLvl;
	int fitnessLvl = myTama.fitnessLvl;
	int socialLvl = myTama.socialLvl;
	double age = myTama.age;
	
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

	static int state = 1; //State 1 = wake/ready, state 2 = working, state 3 = sleep, state 4 = dead, 5 = sick, 6 = rebellious, 7 = in Rock/Paper/Scissors game, 8 = in LuckyDraw game, 9 = in WordGuess game;

	//
	//Method for each button, in the order of which they appear
	//
	public void eatMeal(){
		if(state == 1) myTama.eatMeal();
		else System.out.println("Nope. Can't.");
	}
	public void eatSnack(){
		if(state == 1) myTama.eatSnack();
		else System.out.println("Nope. Can't.");
	}
	public void wash(){
		if(state == 1) myTama.wash();
		else System.out.println("Nope. Can't.");
	}
	public void attention(){
		if(state == 1) myTama.getAttention();
		else System.out.println("Nope. Can't.");
	}
	public void sleep(){
		if(state == 1) timer = new Ticks("sleep");
		else System.out.println("Nope. Can't.");
	}
	public void fitness(){
		if(state == 1) 
			if(myTama.testWilling()) timer = new Ticks("fitness");
			else System.out.println("Don't wanna");
		else System.out.println("Nope. Can't.");
	}
	public void study(){
		if(state == 1) 
			if(myTama.testWilling()) timer = new Ticks("study");
			else System.out.println("Don't wanna");
		else System.out.println("Nope. Can't.");
	}
	public void playGuess(){
		if(state == 1) wg = new WordGuess(myTama);
		else System.out.println("Nope. Can't.");
	}
	public void playCard(){
		if(state == 1) ld = new LuckyDraw(myTama);
		else System.out.println("Nope, can't.");
	}
	public void playRPS(){
		if(state == 1) rps = new RockPaperScissors(myTama);
		else System.out.println("Nope, can't.");
	}
	public void yes(){
		System.out.println("Yes");
		if(state == 7) rps.yes(myTama);
		else if(state == 8) ld.yes(myTama);
		else if(state == 9) wg.yes(myTama);
	}
	public void no(){
		System.out.println("No");
		System.out.println(state);
		if(state == 2) myTama.endStatus(timer);
		else if(state == 3) myTama.wakeUp(timer);
		else if(state == 5) myTama.getBetter(timer);
		else if(state == 7) rps.no();
		else if(state == 8) ld.no();
		else if(state == 9) wg.no();
	}

	public Tama loadTama(int tamaId){
		System.out.println("meth start");
		try{ Statement stmt = con.createStatement();
		System.out.println("create stmt");
		ResultSet tamaRs = stmt.executeQuery("SELECT * FROM tamadb WHERE id = '"+ tamaId +"';");
		System.out.println("tamars");
		if(tamaRs.next()){
			myTama.setAge(tamaRs.getDouble("age"));
			myTama.setFitnessExp(tamaRs.getInt("fitnessexp"));
			myTama.setFitnessLvl(tamaRs.getInt("fitnesslvl"));
			myTama.setIntelExp(tamaRs.getInt("intelexp"));
			myTama.setIntelLvl(tamaRs.getInt("intellvl"));
			myTama.setSocialExp(tamaRs.getInt("socialexp"));
			myTama.setSocialLvl(tamaRs.getInt("sociallvl"));
			myTama.setGameScore(tamaRs.getInt("score"));
			myTama.setTimeSpentDirty(tamaRs.getInt("timespentdirty"));
			myTama.setTimeSpentHealthy(tamaRs.getInt("timespenthealthy"));
			myTama.setTimeSpentUnhappy(tamaRs.getInt("timespentunhappy"));
			myTama.setHappy(tamaRs.getDouble("happy"));
			myTama.setHygiene(tamaRs.getDouble("hygiene"));
			myTama.setHunger(tamaRs.getDouble("hunger"));
			myTama.setSleep(tamaRs.getDouble("sleep"));
			myTama.setSick(tamaRs.getDouble("sick"));
			myTama.setRebel(tamaRs.getDouble("rebel"));
			myTama.setId(tamaRs.getInt("id"));
			myTama.setName(tamaRs.getString("name"));
		}
		} catch(SQLException sqle){
			System.out.println("SQL Exception");
		}
		return myTama;
	}
/*	public void loadTama(){
		System.out.println("meth start");
		try{Statement stmt = con.createStatement();
		System.out.println("create stmt");
		ResultSet tamaRs = stmt.executeQuery("SELECT * FROM tamadb WHERE id = '"+ id +"';");
		System.out.println("tamars");
		if(tamaRs.next()){
			myTama.setAge(tamaRs.getDouble("age"));
			myTama.setFitnessExp(tamaRs.getInt("fitnessexp"));
			myTama.setFitnessLvl(tamaRs.getInt("fitnesslvl"));
			myTama.setIntelExp(tamaRs.getInt("intelexp"));
			myTama.setIntelLvl(tamaRs.getInt("intellvl"));
			myTama.setSocialExp(tamaRs.getInt("socialexp"));
			myTama.setSocialLvl(tamaRs.getInt("sociallvl"));
			myTama.setGameScore(tamaRs.getInt("score"));
			myTama.setTimeSpentDirty(tamaRs.getInt("timespentdirty"));
			myTama.setTimeSpentHealthy(tamaRs.getInt("timespenthealthy"));
			myTama.setTimeSpentUnhappy(tamaRs.getInt("timespentunhappy"));
			myTama.setHappy(tamaRs.getDouble("happy"));
			myTama.setHygiene(tamaRs.getDouble("hygiene"));
			myTama.setHunger(tamaRs.getDouble("hunger"));
			myTama.setSleep(tamaRs.getDouble("sleep"));
			myTama.setSick(tamaRs.getDouble("sick"));
			myTama.setRebel(tamaRs.getDouble("rebel"));
			myTama.setId(tamaRs.getInt("id"));
			myTama.setName(tamaRs.getString("name"));
		}
		} catch(SQLException sqle){
			System.out.println("SQL Exception 2");
		} 
		System.out.println("End");
	}*/
	

/*				System.out.println("Hunger: "+a.hunger+" Hygiene: "+a.hygiene+" Happy: "+a.happy+" Sleep: "+a.sleep);
				System.out.println("What will you do?");
				System.out.println(a.name + " is busy. Stop them?");
				System.out.println(a.name + " is sleeping. Wake them up?");
				System.out.println(a.name + " is sick. Give them medicine?");*/
}