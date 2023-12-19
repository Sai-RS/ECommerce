package com.hexaware.entity;

public class Product {
	
	
	private long productId;
	private String name;
	private double price;
	private String description;
	private int stockQuantity;
	
	public Product() {
		
	}
	
	public Product(long productId, String name, double price, String description, int stockQuantity) {
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.description = description;
		this.stockQuantity = stockQuantity;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	@Override
	public String toString() {
		return "Product Id: " + productId + "\nName: " + name + "\nPrice: " + price + "\nDescription: " + description + "\nStockQuantity: "+stockQuantity;
	}
	
}
