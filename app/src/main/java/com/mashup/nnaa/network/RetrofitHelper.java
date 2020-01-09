package com.mashup.nnaa.network;

import com.mashup.nnaa.network.model.UserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class RetrofitHelper {
    private Retrofit retrofit = null;

    // singleton
    private RetrofitHelper() { retrofit = new Retrofit.Builder().baseUrl(ENTRY_URL).build(); }
    private static final RetrofitHelper _instance = new RetrofitHelper();
    public static RetrofitHelper getInstance() { return _instance; }

    public final String ENTRY_URL = "http://52.78.191.221:8080/";

    public void getUserInfo(Callback<UserInfo> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<UserInfo> userInfo = service.getUserInfo();
        userInfo.enqueue(callback);
    }
}
