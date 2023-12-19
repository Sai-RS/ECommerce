package com.hexaware.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CustomerDataValidation {
	
	public CustomerDataValidation() {
		
	}
	
	public static boolean verifyName(String name) {
		boolean flag = true;
		
		if(!name.matches("^[a-zA-Z]+$")) {
			flag = false;
		}
		
		return flag;
	}
	
	public static boolean verifyEmail(String email) {
		
		boolean flag = true;
		String[] emailArr = email.split("@");
		
		if(emailArr.length!=2) {
			flag = false;
		}
		else if(emailArr[1].split("\\.").length!=2) {
			flag = false;
		}
		
		return flag;
	}
	
	public static boolean verifyContactNumber(String number) {
		boolean flag = true;
		
		if(!number.matches("\\d{3}-\\d{3}-\\d{4}")) {
			flag = false;
		}
		
		return flag;
	}
	
	public static String syntaxChecker(String data,String details) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = data.matches("^[a-zA-Z]+$");
		
		while(!flag){
			String Data;
			System.out.format("The %s Name should not contain special characters%n",details);
			System.out.format("Re-Enter your %s Name: ",details);
			Data = bf.readLine();
			
			flag = Data.matches("^[a-zA-Z]+$") ;
			
			if(flag)
				return Data;
		}
		return data;
		
	}
	
	public static String capitalizeAllWords(String data) {
		String datas[] = data.split("\\s+");
		StringBuilder sb = new StringBuilder();
		
		for(String d:datas) {
			if(!d.isEmpty())
				sb.append(d.substring(0, 1).toUpperCase() + d.substring(1));
		}
		
		return sb.toString();
	}
	
	public static String capitalize(String data) {
		return data.substring(0, 1).toUpperCase() + data.substring(1);
	}
	
	public static String unCapitalize(String data) {
		return data.substring(0, 1).toLowerCase() + data.substring(1);
	}
	
	public static String getAddress() throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		
		boolean flag = true;
		String street,city,state,zipCode;
		
		// Street 
		
		System.out.println("Enter your Street Name: ");
		street = capitalize(syntaxChecker(bf.readLine(),"Street"));
		
		// City 
		
		System.out.println("\nEnter your City Name: ");
		city = capitalize(syntaxChecker(bf.readLine(),"City"));
		
		
		// State 
		
		System.out.println("\nEnter your State Name: ");
		state = capitalize(syntaxChecker(bf.readLine(),"State"));
		
		
		// ZipCode 
		
		System.out.println("\nEnter your ZipCode Number (###-###): ");
		zipCode = bf.readLine();
		
		flag = zipCode.matches("\\d{3}-\\d{3}");
		
		while(!flag){
			System.out.println("The Entered ZipCode is invalid\n");
			System.out.println("Re-Enter your ZipCode Number: ");
			zipCode = bf.readLine();
			
			flag = zipCode.matches("\\d{3}-\\d{3}");		
		}
		
		return street + " Street," + city + "," + state + "," + zipCode;
		
	}

	public static void main(String[] args) throws IOException {
		System.out.println(getAddress());
		

	}

}
