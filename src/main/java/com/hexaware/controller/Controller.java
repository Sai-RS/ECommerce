package com.hexaware.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hexaware.dao.OrderProcessorRepositoryImpl;
import com.hexaware.entity.*;
import com.hexaware.exception.*;
import com.hexaware.util.DBConnection;

public class Controller {
	
	static Connection connection;
	
	public Controller() {
		Controller.connection = DBConnection.getConnection();
	}
	
	public Customer createCustomer() throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

		Customer customer = new Customer();
		
		String name,email,password;
		
		// Name
		
		System.out.println("Enter your name: ");
		name = bf.readLine();
		
		while(!CustomerDataValidation.verifyName(name)) {
			System.out.println("The Name should contain only alphabetical letters.\n");
			System.out.println("Re-Enter your name: ");
			name = bf.readLine();
		}	
		customer.setName(name);
		
		// Email
		
		System.out.println("Enter your email: ");
		email = bf.readLine();
		
		while(!CustomerDataValidation.verifyEmail(email)) {
			System.out.println("The given email is invalid.\n");
			System.out.println("Re-Enter your email: ");
			email = bf.readLine();
		}	
		customer.setEmail(email);
		
		// Password
		
		System.out.println("Enter your password: ");
		password = bf.readLine();
		
		customer.setPassword(password);
		
		return customer;	
		
	}
	
	public Product createProduct() throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

		Product product = new Product();
		
		String name,description;
		int stockQuantity;
		double price;
		
		System.out.println("Enter the product name: ");
		name = CustomerDataValidation.capitalizeAllWords(bf.readLine());
		product.setName(name);
		
		System.out.println("Enter the product description: ");
		description = CustomerDataValidation.capitalizeAllWords(bf.readLine());
		product.setDescription(description);
		
		System.out.println("Enter the stock quantity of the product: ");
		stockQuantity = Integer.parseInt(bf.readLine());
		product.setStockQuantity(stockQuantity);
		
		System.out.println("Enter the price of the product: ");
		price = Double.parseDouble(bf.readLine());
		product.setPrice(price);
		
		return product;
		
	}
	
	public Customer getCustomer(long customerId) {
		Customer customer = new Customer();
		
		PreparedStatement prepareStatement;
		try {
			prepareStatement = connection.prepareStatement("Select * from customer where customer_id = ?");
			prepareStatement.setLong(1, customerId);
			
			ResultSet resultSet = prepareStatement.executeQuery();
			
			if(resultSet.next()) {
				customer = new Customer(resultSet.getLong("customer_id"),resultSet.getString("name"),resultSet.getString("email"),resultSet.getString("password"));
				//customer.setCustomerId(resultSet.getLong());
			
			}
		} 
		catch (SQLException e) {
			
		}
		
		return customer;
	}
	
	public Product getProduct(long productId) {
		Product product = null;
		
		PreparedStatement prepareStatement;
		try {
			prepareStatement = connection.prepareStatement("Select * from products where product_id = ?");
			prepareStatement.setLong(1, productId);
			
			ResultSet resultSet = prepareStatement.executeQuery();
			
			if(resultSet.next()) {
				product = new Product(resultSet.getLong("product_id"),resultSet.getString("name"),resultSet.getDouble("price"),resultSet.getString("description"),resultSet.getInt("stockQuantity")
);
			}
		} 
		catch (SQLException e) {
			product = null;
		}
		
		return product;
	}
	
	public boolean addToCart() throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		OrderProcessorRepositoryImpl opri = new OrderProcessorRepositoryImpl();
		Controller controller  = new Controller();
		
		boolean flag = true;
		
		long customerId,productId;
		int quantity;
		
		System.out.println("Enter the customer Id: ");
		customerId = Long.parseLong(bf.readLine());
		
		Customer customer = controller.getCustomer(customerId);
		
		try {
			if(customer.getCustomerId()==0) {
				throw new CustomerNotFoundException();
			}
			else {
				int choice;
				
				do {
					System.out.println("\nSelect your preference:\n");
					System.out.println("Press\n\t1. Add product to the cart\n\t2. Stop\n");

					System.out.println("Enter your choice: ");
					choice = Integer.parseInt(bf.readLine());
					
					if(choice == 1) {
						System.out.println("\nEnter the product id: ");
						productId = Long.parseLong(bf.readLine());
						
						try {
							Product product = controller.getProduct(productId);
							if(product == null) {
								throw new ProductNotFoundException();
							}
							else {
								System.out.println("Enter the quantity: ");
								quantity = Integer.parseInt(bf.readLine());
								
								if(opri.addToCart(customer, product, quantity)) {
									System.out.println("\n" + product.getName()+ " added to the cart!!!");;
								}
								else {
									System.out.println("\n"+ product.getName()+" couldn't be added to the cart. Try Again!!!");
								}
							}
						
						}
						catch(ProductNotFoundException e) {
							System.out.println(e);
						}
					}
					else if(choice == 2) {
						System.out.println("\nStopped!!!\n");
					}
					else {
						System.out.println("\nEnter a valid choice!!!\n");
					}
				}while(choice!=2);
			}
		}
		catch(CustomerNotFoundException e) {
			System.out.println(e.toString());
		}
		
		
		
		return flag;
	}
	
	public void getFromCart() throws NumberFormatException, IOException {
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		OrderProcessorRepositoryImpl opri = new OrderProcessorRepositoryImpl();
		Controller controller  = new Controller();

		long customerId;
		System.out.println("Enter the customer Id: ");
		customerId = Long.parseLong(bf.readLine());
		
		try {
			Customer customer = controller.getCustomer(customerId);
			if(customer.getCustomerId()==0) {
				throw new CustomerNotFoundException();
			}
			else {
				List<Product> products = opri.getAllFromCart(customer);
				
				for(Product product:products) {
					System.out.println(product.getName() + " " + product.getStockQuantity());
				}
				System.out.println("\n");
			}
		}
		catch(CustomerNotFoundException e) {
			System.out.println(e.toString());
		}
		
	}
	
	public Map<Product,Integer> geFromCartForOrders(Customer customer) {
		Controller controller  = new Controller();

		Map<Product,Integer> products = new HashMap<>();
		
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("Select * from cart where customer_id = ?");
			prepareStatement.setLong(1,customer.getCustomerId());
			
			ResultSet resultSet = prepareStatement.executeQuery();
			
			while(resultSet.next()) {
				Product product = controller.getProduct(resultSet.getLong("product_id"));
				int quantity = resultSet.getInt("quantity");
				//Product product = new Product(resultSet.getLong("product_id"),resultSet.getString("name"),resultSet.getDouble("price"),resultSet.getString("description"),resultSet.getInt("quantity"));
				
				products.put(product, quantity);
			}
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return products;
	}
	
	public void placeOrder() throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		OrderProcessorRepositoryImpl opri = new OrderProcessorRepositoryImpl();
		Controller controller  = new Controller();
		
		long customerId;
		List<Map<Product, Integer>> productList = new ArrayList<>();
		System.out.println("Enter the customer Id: ");
		customerId = Long.parseLong(bf.readLine());
		
		try {
			Customer customer = controller.getCustomer(customerId);
			
			if(customer.getCustomerId()==0) {
				throw new CustomerNotFoundException();
			}
			else {
				productList.add(controller.geFromCartForOrders(customer));
			}
			
			String street,city,state,pincode,address;
			
			System.out.println("Enter your street name: ");
			street = bf.readLine();
			
			System.out.println("Enter your city name: ");
			city = bf.readLine();
			
			System.out.println("Enter your state name: ");
			state = bf.readLine();
			
			System.out.println("Enter your pincode: ");
			pincode = bf.readLine();
			
			address = street + " " + city + " " + state + " " + pincode;
			
			opri.placeOrder(customer, productList, address);
			
		}
		catch(CustomerNotFoundException e) {
			System.out.println(e.toString());
		}
	}
	
	public void insertOrderItems(long orderId,Product product,int quantity) {
		
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("insert into order_items (order_id,product_id,quantity) values (?,?,?)");
			
			prepareStatement.setLong(1, orderId);
			prepareStatement.setLong(2, product.getProductId());
			prepareStatement.setInt(3, quantity);
			
			prepareStatement.execute();
			
		} 
		catch (SQLException e) {
			
			System.out.println(e);
		}
		
	}
	
	public void viewCustomerOrders() throws NumberFormatException, IOException {
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		OrderProcessorRepositoryImpl opri = new OrderProcessorRepositoryImpl();
		Controller controller  = new Controller();

		long customerId;
		List<Map<Product, Integer>> productList = new ArrayList<>();
		System.out.println("Enter the customer Id: ");
		customerId = Long.parseLong(bf.readLine());
		
		try {
			Customer customer = controller.getCustomer(customerId);
			
			if(customer.getCustomerId()==0) {
				throw new CustomerNotFoundException();
			}
			else {
				productList = opri.getOrdersByCustomer(customerId);
				
				for(Map<Product,Integer> m:productList) {
					for (Map.Entry<Product, Integer> entry : m.entrySet()) {
		                Product product = entry.getKey();
		                int quantity = entry.getValue();
		                
		                System.out.println(product.getName());
					}
				}
			}
		}
		catch(CustomerNotFoundException e) {
			System.out.println(e.toString());
		}
	}
	
	public static void main(String[] args) {
		Controller controller  = new Controller();

		System.out.println(controller.getCustomer(1));
	}
}
