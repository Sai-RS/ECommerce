package com.hexaware.dao;

import java.util.List;
import java.util.Map;

import com.hexaware.entity.*;

public interface OrderProcessorRepository {
	
	boolean createProduct(Product product);
	boolean createCustomer(Customer customer);
	boolean deleteProduct(long productId);
	boolean deleteCustomer(long customerId);
	boolean addToCart(Customer customer,Product product,int quantity);
	boolean removeFromCart(Customer customer,Product product);
	List<Product> getAllFromCart(Customer customer);
	boolean placeOrder(Customer customer,List<Map<Product,Integer>> productList, String Address); // Update order and orderItem table
	List<Map<Product,Integer>> getOrdersByCustomer(long customerId);
	
}


