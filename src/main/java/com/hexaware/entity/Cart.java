package com.hexaware.entity;

public class Cart {

	private long cartId;
	private Customer customer;
	private Product product;
	private int quantity;
	
	public Cart() {
		
	}
	
	public Cart(long cartId, Customer customer, Product product, int quantity) {
		this.cartId = cartId;
		this.customer = customer;
		this.product = product;
		this.quantity = quantity;
	}

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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
		return "Cart Id: " + cartId + "\nCustomer Name: " + customer.getName() + "\nProduct Name: " + product.getName() + "\nQuantity: " + quantity;
	}
	
	
}
