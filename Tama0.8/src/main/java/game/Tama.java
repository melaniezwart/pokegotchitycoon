package game;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import db.DatabaseConnect;

public class Tama extends DatabaseConnect{
	
	Ticks lifeTimer;
	String name = "Tammo";
	int id;
	double age = 0;
	double hunger = 22;
	double happy = 50;
	double hygiene = 50;
	double sleep = 80;
	
	double sick = 0;
	double rebel = 0;
	
	int intelLvl = 1;
	int intelExp = 0;
	int fitnessLvl = 1;
	int fitnessExp = 0;
	int socialLvl = 1;
	int socialExp = 0;
	
	int gameScore = 0;
	
	int timeSpentHealthy = 0;
	
	int timeSpentDirty = 0;
	int timeSpentUnhappy = 0;
	Ticks rebelTimer;
	Ticks sickTimer;
	Ticks timer;
	
	public Tama(){
		
	}
	public Tama(String name){
		this.name = name;
	}
	
	public void setName(String name){
		this.name = name;
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
	
	protected void getAttention(){
		System.out.println("Getting attention");
		this.happy += 20;
		if(this.happy > 100){
			this.happy = 100;
			System.out.println("Al helemaal happy");
		}
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
		if(this.hunger < 0)			death("starved");
		else if(this.hunger <5)		System.out.println("Time is ticking out, please feed " + this.name + " now.");
		else if (this.hunger < 10) 	System.out.println(this.name + " is starving. Please feed them.");
		else if (this.hunger < 20) 	System.out.println(this.name + " is getting hungry. You should feed them soon.");

		if(this.happy <5) 			System.out.println(this.name + " probably won't listen to you anymore until you give them some love and attention."); 
		else if (this.happy < 10) 	System.out.println(this.name + " really needs some attention.");
		else if (this.happy < 20) 	System.out.println(this.name + " is getting unhappy. Play with them or give them some love.");
		
		if(this.hygiene <5) 		System.out.println("It smells like something's dying in here. " + this.name + " could get sick from this."); 
		else if (this.hygiene < 10) System.out.println(this.name + " stinks.");
		else if (this.hygiene < 20) System.out.println(this.name + " is starting to smell.");
		
		if (Game.state != 3){
			if(this.sleep <= 0) {
				Game.state = 3;
				System.out.println(this.name + " fell asleep.");
			} else if (this.sleep <5) System.out.println(this.name + " could fall asleep any minute now.");
			else if (this.sleep < 10) System.out.println(this.name + " can barely keep their eyes open.");
			else if (this.sleep < 20) System.out.println(this.name + " is getting sleepy.");
		}
		
		//Tests if tama is well looked after and adds points to a variable. Important for score and random events.
		if(this.hunger > 50 && this.happy > 50 && this.hygiene > 50 && this.sleep > 50)
			this.timeSpentHealthy++;
		
		if(this.hygiene < 5) this.timeSpentDirty += 5;
		else if(this.hygiene < 10) this.timeSpentDirty += 2;
		else if(this.hygiene < 20) this.timeSpentDirty++;
		else if(this.hygiene > 80) this.timeSpentDirty--;
		else if(this.hygiene > 90) this.timeSpentDirty -= 3;
		
		if(this.happy < 5) this.timeSpentUnhappy += 5;
		else if(this.happy < 10) this.timeSpentUnhappy += 2;
		else if(this.happy < 20) this.timeSpentUnhappy++;
		else if(this.happy > 80) this.timeSpentUnhappy--;
		else if(this.happy > 90) this.timeSpentUnhappy -= 3;
	}
	
	protected void death(String cause){	//Prints the cause of death, then some information on the tama. 
		int score = (timeSpentHealthy * 5) + intelExp + socialExp + fitnessExp;
		gameScore = score;
		Game.state = 4;
		switch (cause){
		case "starved": 
			System.out.println(this.name + " starved to death. Rest in piece, poor neglected soul. You were too good for this world.");
			break;
		case "sick": 
			System.out.println(this.name + " died of an illness. Poor thing. Maybe you should've taken better care of them. Rest in piece. You deserved better.");
			break;
		case "rebel":
			System.out.println(this.name +  " has run away and is never coming back. At least, that's what it said in their goodbye letter. Good luck, buddy. Game over.");
			break;
		}
		System.out.print(this.name + " was " + this.age + " days old. \n"
				+ "Their intelligence level was " + this.intelLvl + "\n"
				+ "Their fitness level was " + this.fitnessLvl+"\n"
				+ "Your total score is: " + score + "\n");
		Game.endGame();
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
		
		if(rebelNumber > 110) rebelTimer = new Ticks("rebel");
		if(sickNumber > 105) sickTimer = new Ticks("sick");

	}
	
	public void save() throws SQLException{
		String msg = "UPDATE tamadb SET (id, name, score, hunger, happy, hygiene, sleep, sick, "
				+ "rebel, intelLvl, intelExp, fitnessExp, socialLvl, socialExp, "
				+ "timeSpentHealthy, timeSpentDirty, timeSpentUnhealthy) "
				+ "= ("+this.id+", "+this.name+", "+this.gameScore+", "+this.hunger+ ", "
				+ this.happy+", "+this.hygiene+", "+this.sleep+", "+this.rebel+", "
				+ this.intelLvl+", "+this.intelExp+", "+this.fitnessLvl+", "+this.fitnessExp+ ", "
				+ this.socialLvl+", "+this.socialExp+", "+this.timeSpentHealthy+", "
				+ this.timeSpentDirty+", "+this.timeSpentUnhappy+")";
		
		PreparedStatement ps = con.prepareStatement(msg);
		ps.executeUpdate();
	}
/*	CREATE TABLE IF NOT EXISTS tamadb (
			id int PRIMARY KEY AUTO_INCREMENT,
			name VARCHAR(25),
			score int,
			hunger double,
			happy double,
			hygiene double,
			sleep double,
			sick double,
			rebel double,
			intelLvl int,
			intelExp int,
			fitnessLvl int,
			fitnessExp int,
			socialLvl int,
			socialExp int,
			timeSpentHealthy int,
			timeSpentDirty int,
			timeSpentUnhappy int
			);*/
	public void setId(int id) {
		this.id = id;
	}
	public void setAge(double age) {
		this.age = age;
	}
	public void setHunger(double hunger) {
		this.hunger = hunger;
	}
	public void setHappy(double happy) {
		this.happy = happy;
	}
	public void setHygiene(double hygiene) {
		this.hygiene = hygiene;
	}
	public void setSleep(double sleep) {
		this.sleep = sleep;
	}
	public void setSick(double sick) {
		this.sick = sick;
	}
	public void setRebel(double rebel) {
		this.rebel = rebel;
	}
	public void setIntelLvl(int intelLvl) {
		this.intelLvl = intelLvl;
	}
	public void setIntelExp(int intelExp) {
		this.intelExp = intelExp;
	}
	public void setFitnessLvl(int fitnessLvl) {
		this.fitnessLvl = fitnessLvl;
	}
	public void setFitnessExp(int fitnessExp) {
		this.fitnessExp = fitnessExp;
	}
	public void setSocialLvl(int socialLvl) {
		this.socialLvl = socialLvl;
	}
	public void setSocialExp(int socialExp) {
		this.socialExp = socialExp;
	}
	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}
	public void setTimeSpentHealthy(int timeSpentHealthy) {
		this.timeSpentHealthy = timeSpentHealthy;
	}
	public void setTimeSpentDirty(int timeSpentDirty) {
		this.timeSpentDirty = timeSpentDirty;
	}
	public void setTimeSpentUnhappy(int timeSpentUnhappy) {
		this.timeSpentUnhappy = timeSpentUnhappy;
	}
	public void setRebelTimer(Ticks rebelTimer) {
		this.rebelTimer = rebelTimer;
	}
	public void setSickTimer(Ticks sickTimer) {
		this.sickTimer = sickTimer;
	}
	
	public void passTime(){
		System.out.println("pass time");
		double ticksPassed = 0;
		this.age += (++ticksPassed/30);
		this.hunger--;
		this.happy--;
		this.hygiene--;
		this.sleep--;
		this.testHealth();
		
		if(this.hygiene < 0) this.hygiene = 0;
		if(this.happy < 0) this.happy = 0;
		System.out.println(this.hunger + ", " + this.happy);
		
		this.randomEventListener();
	}
	
	public void studyTask(){
		this.intelExp += 5;
		this.happy -= 0.2;
		this.levelUp();
		System.out.println("Studying");
		if(this.hunger < 20){
			System.out.println("Too hungry");
			Game.timer.endStatus();
		} else if (this.happy < 20){
			System.out.println("Too unhappy");
			Game.timer.endStatus();
		} else if (this.sleep < 20){
			System.out.println("Getting sleepy");
			Game.timer.endStatus();
		}
	}
	public void fitnessTask(){
		this.fitnessExp += 5;
		this.hunger -= 0.1;
		this.hygiene -= 0.1;
		this.sleep -= 0.1;
		this.levelUp();
		System.out.println("Working out");
		if(this.hunger < 20){
			System.out.println("Too hungry");
			Game.timer.endStatus();
		} else if (this.happy < 20) {
			System.out.println("Too unhappy");
			Game.timer.endStatus();
		} else if (this.sleep < 20){
			System.out.println("Getting sleepy");
			Game.timer.endStatus();
		}
	}
	public void sleepTask(){
		this.sleep += 2;
		if(this.sleep >= 100){
			this.sleep = 100;
			Game.timer.wakeUp();
		}
		if(this.happy < 5){
			Game.timer.wakeUp();
			System.out.println(this.name + " woke up feeling miserable. Give them some love.");
		} else if (this.hunger < 10){
			System.out.println(this.name + " woke up feeling hungry and ready for breakfast.");
		}
	}
	
	public void sickTask(){
		this.sick += 5;
		this.sleep -= 2;
		this.hygiene -= 2;
		this.hunger -= 2;
		if(this.sick >= 100){
			this.death("sick");
		}
	}
	public void rebelTask(){
		this.rebel += 5;
		this.happy -= 3;
		if(this.rebel == 100){
			this.death("rebel");
		}
	}
	
	public void endStatus(Ticks timer){
		System.out.println(this.name + " has stopped.");
		timer.statusTicks.cancel();
		Game.state = 1;
	}
	public void wakeUp(Ticks timer){
		System.out.println(this.name + " just woke up.");
		timer.statusTicks.cancel();
		Game.state = 1;
	}
	public void getBetter(Ticks timer){
		System.out.println(this.name + " has recovered.");
		timer.statusTicks.cancel();
		Game.state = 1;
	}
	public void tellOff(){
		System.out.println(this.name + " will behave now.");
		this.rebelTimer.statusTicks.cancel();
		Game.state = 1;
	}
/*	CREATE TABLE IF NOT EXISTS users (
		id BIGSERIAL PRIMARY KEY,
		name VARCHAR(25),
		password VARCHAR(25),
		tamas int[]
 */
	
}
