package com.app.request.exception;

public class UnableToDeleteMediaException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Unable To Delete Request.";
    }

}
