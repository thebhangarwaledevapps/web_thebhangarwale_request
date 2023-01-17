package com.app.request.exception;

public class InvalidItemQuantityException extends Exception{

	@Override
	public String getMessage() {
		return "Enter Valid Item Quantity.";
	}

}