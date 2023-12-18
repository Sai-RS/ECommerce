package com.hexaware.dao;

import java.util.List;
import java.util.Map;

import com.hexaware.entity.Customer;
import com.hexaware.entity.Product;

public class OrderProcessorRepositoryImpl implements OrderProcessorRepository{

	@Override
	public boolean createProduct(Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProduct(long productId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCustomer(long customerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addToCart(Customer customer, Product product, int quantity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFromCart(Customer customer, Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Product> getAllFromCart(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean placeOrder(Customer customer, List<Map<Product, Integer>> productList, String Address) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Map<Product, Integer>> getOrdersByCustomer(long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
