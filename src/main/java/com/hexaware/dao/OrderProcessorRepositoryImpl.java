package com.hexaware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hexaware.controller.Controller;
import com.hexaware.entity.Customer;
import com.hexaware.entity.Order;
import com.hexaware.entity.Product;
import com.hexaware.util.DBConnection;

public class OrderProcessorRepositoryImpl implements OrderProcessorRepository{

	static Connection connection;
	
	public OrderProcessorRepositoryImpl() {
		OrderProcessorRepositoryImpl.connection = DBConnection.getConnection();
	}
	
	@Override
	public boolean createProduct(Product product) {
		boolean flag = true;
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("insert into products (Name,description,price,stockQuantity) values (?,?,?,?)");
			
			prepareStatement.setString(1,product.getName() );
			prepareStatement.setString(2, product.getDescription());
			prepareStatement.setDouble(3, product.getPrice());
			prepareStatement.setInt(4, product.getStockQuantity());
			
			prepareStatement.execute();
			
		} 
		catch (SQLException e) {
			flag = false;
			System.out.println(e);
		}
		return flag;
	}

	@Override
	public boolean createCustomer(Customer customer) {
		
		boolean flag = true;
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("insert into customer (Name,Email,password) values (?,?,?)");
			
			prepareStatement.setString(1,customer.getName() );
			prepareStatement.setString(2, customer.getEmail());
			prepareStatement.setString(3, customer.getPassword());
			
			prepareStatement.execute();
			
		} 
		catch(SQLIntegrityConstraintViolationException e) {
			flag = false;
			System.out.println("The Email already exists. Provide an alternative email.");
			
		}
		catch (SQLException e) {
			flag = false;
			System.out.println(e);
		}
		return flag;
	}

	@Override
	public boolean deleteProduct(long productId) {
		
		boolean flag = true;
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement("delete from products where productId = ?");
			preparedStatement.setLong(1,productId );
			
			preparedStatement.execute();
			
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public boolean deleteCustomer(long customerId) {
		
		boolean flag = true;
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("delete from customer where customerId = ?");
			preparedStatement.setLong(1,customerId );
			
			preparedStatement.execute();
			
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		}
		
		
		return flag;
	}

	@Override
	public boolean addToCart(Customer customer, Product product, int quantity) {
		
		boolean flag = true;
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("insert into cart (customer_id,product_id,quantity) values (?,?,?)");
			
			prepareStatement.setLong(1, customer.getCustomerId() );
			prepareStatement.setLong(2, product.getProductId());
			prepareStatement.setInt(3, quantity);
			
			prepareStatement.execute();
			
		} 
		catch (SQLException e) {
			flag = false;
			System.out.println(e);
		}
		return flag;
	}

	@Override
	public boolean removeFromCart(Customer customer, Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Product> getAllFromCart(Customer customer) {
		Controller controller  = new Controller();

		List<Product> products = new ArrayList<>();
		
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("Select * from cart where customer_id = ?");
			prepareStatement.setLong(1,customer.getCustomerId());
			
			ResultSet resultSet = prepareStatement.executeQuery();
			
			while(resultSet.next()) {
				Product product = controller.getProduct(resultSet.getLong("product_id"));
				//Product product = new Product(resultSet.getLong("product_id"),resultSet.getString("name"),resultSet.getDouble("price"),resultSet.getString("description"),resultSet.getInt("quantity"));
				
				products.add(product);
			}
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return products;
	}

	@Override
	public boolean placeOrder(Customer customer, List<Map<Product, Integer>> productList, String Address) {
		Controller controller  = new Controller();

		boolean flag = true;
		long orderId;
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("insert into orders (customer_id,order_date,total_price,street,city,state,pincode) values (?,current_date,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			double total_price = 0;
			
			for(Map<Product,Integer> m:productList) {
				for (Map.Entry<Product, Integer> entry : m.entrySet()) {
	                Product product = entry.getKey();
	                int quantity = entry.getValue();
	                
	                total_price+=product.getPrice()*quantity;
				}
			}
			
			String addressArr[] = Address.split(" ");
			
			prepareStatement.setLong(1, customer.getCustomerId() );
			prepareStatement.setDouble(2, total_price);
			prepareStatement.setString(3, addressArr[0]);
			prepareStatement.setString(4, addressArr[1]);
			prepareStatement.setString(5, addressArr[2]);
			prepareStatement.setString(6, addressArr[3]);
			
			prepareStatement.executeUpdate();
			
			ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
			if(generatedKeys.next()) {
				orderId = generatedKeys.getInt(1);
				
				for(Map<Product,Integer> m:productList) {
					for (Map.Entry<Product, Integer> entry : m.entrySet()) {
		                Product product = entry.getKey();
		                int quantity = entry.getValue();
		                
		                controller.insertOrderItems(orderId,product, quantity);
					}
				}
			}
		} 
		catch (SQLException e) {
			flag = false;
			System.out.println(e);
		}
		return flag;
	}

	@Override
	public List<Map<Product, Integer>> getOrdersByCustomer(long customerId) {
		
		Controller controller  = new Controller();

		List<Map<Product,Integer>> map = new ArrayList();
		Map<Product,Integer> m = new HashMap<>();
		
		PreparedStatement prepareStatement;
		try {
			prepareStatement = connection.prepareStatement("select product_id,quantity from orders o join order_items ot on o.order_id = ot.order_id where customer_id = ?");
			prepareStatement.setLong(1,customerId);
			
			ResultSet resultSet = prepareStatement.executeQuery();
			
			while(resultSet.next()) {
				Product product = controller.getProduct(resultSet.getLong("product_id"));
				int quantity = resultSet.getInt("quantity");
				
				m.put(product, quantity);
			}
			map.add(m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}

}
