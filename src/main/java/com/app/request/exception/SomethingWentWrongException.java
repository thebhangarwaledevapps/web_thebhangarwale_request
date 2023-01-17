package com.app.request.exception;

public class SomethingWentWrongException extends RuntimeException{

    @Override
    public String getMessage() {
        return "error_something_went_wrong";
    }
}
