package com.hexaware.entity;

import java.util.Date;

public class Order {
	
	private long orderId;
	private Customer customer;
	private Date orderDate;
	private double totalPrice;
	private String street;
	private String city;
	private String state;
	private String pincode;
	
	public Order() {
		
	}
	
	public Order(long orderId, Customer customer, Date orderDate, double totalPrice, String street, String city,
			String state, String pincode) {
		super();
		this.orderId = orderId;
		this.customer = customer;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	@Override
	public String toString() {
		return "Order Id: " + orderId + "\nCustomer Name: " + customer.getName() + "\nOrder Date: " + orderDate + "\nTotalPrice: " + totalPrice +
				"\nStreet: " + street + "\nCity: " + city + "\nState: " + state + "\nPincode: " + pincode;
	}
	

}
