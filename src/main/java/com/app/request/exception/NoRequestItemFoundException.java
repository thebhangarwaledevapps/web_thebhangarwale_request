package com.app.request.exception;

public class NoRequestItemFoundException extends Exception {

    @Override
    public String getMessage() {
        return "Something went wrong.";
    }
}
