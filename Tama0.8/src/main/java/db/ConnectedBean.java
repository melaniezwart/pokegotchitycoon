package db;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import game.*;

@ManagedBean(name="bean")
@ApplicationScoped
public class ConnectedBean extends DatabaseConnect{

	private String username;
	private String password;
	private String tamaName;
	private static int playerId;
	//public Players p;
	//public static Game game;
	@Inject
	private Game game;
	
	//Getters and setters
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTamaName() {
		return tamaName;
	}
	public void setTamaName(String tamaName) {
		this.tamaName = tamaName;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		ConnectedBean.playerId = playerId;
	}
	
	public String login() throws IOException, SQLException{
		//Creates statement for connection
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM player WHERE name = '"+username+"' AND password = md5('"+password+"');");
		//Sends query to database checking username and password
		if(rs.next()){
			//If login is correct
			int id = rs.getInt("id"); //Puts the user id in the variable 'id'
			System.out.println(id);
			setPlayerId(id);
			System.out.println(getPlayerId());
			rs = stmt.executeQuery("SELECT tama FROM player WHERE id = '" + playerId + "';");
			rs.next(); //So long as user exists, rs.next always returns true, so it doesn't need an if-statement
			int key = rs.getInt("tama"); //Retrieves the tama id, if there is one
			System.out.println(getPlayerId());
			if(key != 0){
				game.setId(key);
				//Has a current tama
				//Tama tama = MethodDump.loadTama(a); //TODO maybe use the loadTama method that's already in Game or Tama?
				//game = new Game(); //Starts the game, using the tama that was loaded in
				//return "home.xhtml?faces-redirect=true&id=" + id; //Redirects to the game
				System.out.println(getPlayerId());
				return "home.xhtml";
			} else {
				//No current tama, go to screen where you make a new tama
				System.out.println(getPlayerId());
				return "start.xhtml";
			}
		} else {
			//If login is incorrect
			System.out.println("Try again");
			return null;
		}
	}
	
	public void register() throws IOException, SQLException{
		//Creates a new row in the player database, using the username and password entered.
		Statement stmt = con.createStatement();
		//stmt.executeUpdate("INSERT INTO player (username, password) VALUES ('"
		stmt.executeUpdate("INSERT INTO player (name, password) VALUES ('"
				+username+"', md5('"+password+"'));");
		System.out.println("Successfully registered");
	}
	
	//Creates a new tama and inserts Tama name and player id into database. Also updates the player with current game id.
	
	public String createTama() throws IOException, SQLException{
		System.out.println(getPlayerId());
		Statement stmt = con.createStatement();
		//stmt.executeUpdate("INSERT INTO tamadb (name, playerid) VALUES ('"+tamaName+"', '"+playerId+"');", 
		stmt.executeUpdate("INSERT INTO tamadb (name) VALUES ('"+tamaName+"');", 
				+ Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.getGeneratedKeys();
		int key = 0;
		System.out.println(getPlayerId());
		if(rs.next()){
			//Returns the tamaId and inserts it into the player database.
			key = rs.getInt("id");
			//game.setId(key);
			game.startGame(key);
			System.out.println(key + ", " + getPlayerId());
			//stmt.executeUpdate("UPDATE player SET tamaid = '"+key+"' WHERE id = '"+playerId+"';");
			stmt.executeUpdate("UPDATE player SET tama = '"+key+"' WHERE id = '"+getPlayerId()+"';");
		}
		//Tama tama = new Tama(tamaName);
		//game = new Game(); //TODO turn this into the constructor in Tama or Game
		
		//return "home.xhtml?faces-redirect=true&id=" + key;
		return "home.xhtml";
	}
	

}
