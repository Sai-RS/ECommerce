package com.hexaware.exception;

public class CustomerNotFoundException extends Exception{
	
	@Override
	public String toString() {
		return "The Customer Id is invalid!\n";
	}

}
