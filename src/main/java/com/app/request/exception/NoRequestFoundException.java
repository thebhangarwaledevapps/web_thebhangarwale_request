package com.app.request.exception;

public class NoRequestFoundException extends Exception{

    @Override
    public String getMessage() {
        return "No Request Found.";
    }

}