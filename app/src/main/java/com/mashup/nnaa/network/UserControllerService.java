package com.mashup.nnaa.network;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserControllerService {

    @POST("user/email")
    Call<ResponseBody> signInOrRegEmail(@Body HashMap<String, String> body);

    @POST("user/social")
    Call<ResponseBody> signInOrRegSocial(@Body HashMap<String, String> body);
}
