package com.mashup.nnaa.util;

import com.mashup.nnaa.network.UserAuthHeaderInfo;

public class AccountManager {
    private static AccountManager instance = new AccountManager();
    public static AccountManager getInstance() { return instance; }
    private AccountManager() {}

    private UserAuthHeaderInfo userAuthHeaderInfo;
    public UserAuthHeaderInfo getUserAuthHeaderInfo() { return userAuthHeaderInfo; }

    public void setUserAuthHeaderInfo(UserAuthHeaderInfo info) {
        this.userAuthHeaderInfo = info;
    }

    public void setUserAuthHeaderInfo(String userId, String token) {
        this.userAuthHeaderInfo = new UserAuthHeaderInfo(userId, token);
    }
}
