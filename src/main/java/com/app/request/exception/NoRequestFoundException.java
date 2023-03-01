package com.app.request.exception;

public class NoRequestFoundException extends RuntimeException{

    public NoRequestFoundException(String message) {
        super(message);
    }
}