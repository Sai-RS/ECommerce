package com.hexaware.util;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
	static Connection connection;
	
	public static Connection getConnection() {
		try {
			connection = PropertyUtil.getPropertyString();
		} 
		catch (SQLException e) {
			System.out.println(e);
		}
		return connection;
	}
	
	public static void main(String[] args) {
		System.out.println(DBConnection.getConnection());
	}
}
