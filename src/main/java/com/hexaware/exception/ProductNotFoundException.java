package com.hexaware.exception;

public class ProductNotFoundException extends Exception{
	
	@Override
	public String toString() {
		return "The Product Id is invalid";
	}
}
