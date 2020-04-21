package com.mashup.nnaa.network;

public class UserAuthHeaderInfo {
    private String token;
    private String userId;
    private String name;

    public UserAuthHeaderInfo(String userId, String name, String token) {
        this.userId = userId;
        this.token = token;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
