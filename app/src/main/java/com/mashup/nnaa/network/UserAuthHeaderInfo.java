package com.mashup.nnaa.network;

public class UserAuthHeaderInfo {
    private String token;
    private String userId;

    public UserAuthHeaderInfo(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }
}
