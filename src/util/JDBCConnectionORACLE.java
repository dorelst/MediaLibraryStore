package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnectionORACLE {
	
	public static Connection connection = null;

	public static Connection getConnection() {
		try {
			if (connection == null) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "test");
				return connection;
								
			} else {
				return connection;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}
	
	public static void main(String[] args) {
		Connection conn1 = getConnection();
		Connection conn2 = getConnection();
		Connection conn3 = getConnection();
		
		System.out.println(conn1);
		System.out.println(conn2);
		System.out.println(conn3);
	}
}
