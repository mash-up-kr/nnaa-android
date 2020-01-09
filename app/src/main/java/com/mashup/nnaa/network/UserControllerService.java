package com.mashup.nnaa.network;

import com.mashup.nnaa.network.model.UserInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserControllerService {
    @GET("user/login")
    Call<UserInfo> getUserInfo();
}
