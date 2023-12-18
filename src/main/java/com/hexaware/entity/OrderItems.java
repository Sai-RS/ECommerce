package com.hexaware.entity;

public class OrderItems {
	
	private long orderItemId;
	private Order order;
	private Product product;
	private int quantity;
	
	public OrderItems() {
		
	}

	public OrderItems(long orderItemId, Order order, Product product, int quantity) {
		super();
		this.orderItemId = orderItemId;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	public long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	
	public String toString() {
		return "Order Item Id: " + orderItemId + "\nOrder Id: " + order.getOrderId() + "Product Name: " + product.getName() + "\nQuantity" + quantity;
	}

}
