package com.hexaware.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Select your preference:\n");
		System.out.println("Press\n\t1. Register Customer\n\t2. Create Product\n\t3. Delete Product\n\t4. Add to Cart\n\t5. View Cart\n\t6. Place Order\n\t7. View Customer Order\n");

		System.out.println("Enter your choice: ");
		int choice = Integer.parseInt(bf.readLine());	
		do {
			
		}while(true);
	}

}
