package com.app.request.exception;

public class InvalidItemQuantityException extends RuntimeException{
	public InvalidItemQuantityException(String message) {
		super(message);
	}
}