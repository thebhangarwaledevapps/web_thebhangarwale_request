package com.app.request.exception;

public class InvalidMediaSizeException extends Exception{

    @Override
    public String getMessage() {
        return "You can upload maximum 3 media";
    }

}