package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnection {
	
	public static Connection connection = null;
	
	public static Connection getConnection() {
		try {
			if (connection == null) {
				//Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medialibrarystore", "root", "test");
				return connection;
			} else {
				return connection;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
