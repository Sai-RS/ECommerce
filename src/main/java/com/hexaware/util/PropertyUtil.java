package com.hexaware.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PropertyUtil {
	public static Connection getPropertyString() throws SQLException {
		String fileName="src/main/resources/db.properties";
		Properties props = new Properties();
		FileInputStream fis =null;
		
		try {
			fis= new FileInputStream(fileName);
			props.load(fis);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		String url = props.getProperty("db.url");
		String un =props.getProperty("db.username");
		String pwd =props.getProperty("db.password");
		
		return DriverManager.getConnection(url,un,pwd);
		
	}
}
