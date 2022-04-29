package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
	
	//
	private static String connectionURL = "jdbc:postgresql://chunee.db.elephantsql.com:5432/ihkakuci",
			username = "ihkakuci",
			password = "v9eBh97j3vsIOun5-OMwYQOtJw7RliY5";
	
	//
	private static Connection connection;
	
	//
	public static Connection getConnection() {
		
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(connectionURL, username, password);
				
			}
			
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		} return null;
		
	}
	

}
