package com.hexaware.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.hexaware.controller.Controller;
import com.hexaware.dao.OrderProcessorRepositoryImpl;
import com.hexaware.entity.Product;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		Controller c = new Controller();
		OrderProcessorRepositoryImpl opri = new OrderProcessorRepositoryImpl();
		int choice;
		
		do {
			
			System.out.println("\nSelect your preference:\n");
			System.out.println("Press\n\t1. Register Customer\n\t2. Create Product\n\t3. Delete Product\n\t4. Add to Cart\n\t5. View Cart\n\t6. Place Order\n\t7. View Customer Order\n\t8. Stop\n");

			System.out.println("Enter your choice: ");
			choice = Integer.parseInt(bf.readLine());	
			
			if(choice == 1) {
				if(opri.createCustomer(c.createCustomer())) {
					System.out.println("\nCustomer added successfully!!!");
				}
				else {
					System.out.println("\nCouldn't add the customer!\n");
				}
			}
			else if(choice == 2) {
				if(opri.createProduct(c.createProduct())) {
					System.out.println("\nProduct added to the database!!!");
				}
				else {
					System.out.println("\nCouldn't add the product!\n");
				}
			}
			else if(choice == 3) {
				if(opri.deleteProduct(choice)) {
					System.out.println("\nDeleted the product!!!\n");
				}
				else {
					System.out.println("\nCouldn't delete the product!\n");
				}
			}
			else if(choice == 4) {
				c.addToCart();
			}
			else if(choice == 5) {
				c.getFromCart();
			}
			else if(choice == 6) {
				c.placeOrder();
				System.out.println("Order made successfully!!!");
			}
			else if(choice == 7) {
				c.viewCustomerOrders();
			}
			else if(choice == 8) {
				System.out.println("Operation stopped\n\n");
			}
			else {
				System.out.println("Enter a valid choice\n");
			}
		}while(choice!=8);
	}

}
