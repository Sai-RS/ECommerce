package com.hexaware.exception;

public class OrderNotFoundException extends Exception{

	@Override
	public String toString() {
		return "The Order id is invalid";
	}
}
