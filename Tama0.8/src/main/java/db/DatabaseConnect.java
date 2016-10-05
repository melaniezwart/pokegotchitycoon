package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {

	public static Connection con;

	public static Connection getConnection() {

		String url = "jdbc:postgresql://localhost:5432/tamagotchi";
		String user = "user1";
		String password = "user1";
/*		String url = "rdbms.strato.de/DB2716860";
		String user = "U2716860";
		String password = "rycbar124";*/
		try {
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection completed.");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
		}
		return con;
	}
}
