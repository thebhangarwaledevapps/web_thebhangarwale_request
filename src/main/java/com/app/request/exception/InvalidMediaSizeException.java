package com.app.request.exception;

public class InvalidMediaSizeException extends RuntimeException{

    public InvalidMediaSizeException(String message) {
        super(message);
    }
}