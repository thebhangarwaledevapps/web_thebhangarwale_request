package com.app.request.entity;

public class GoogleImageUploadToken {

    private String access_token;

    public GoogleImageUploadToken(String access_token) {
        this.access_token = access_token;
    }

    public GoogleImageUploadToken() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
