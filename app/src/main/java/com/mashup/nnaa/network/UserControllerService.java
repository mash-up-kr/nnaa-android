package com.mashup.nnaa.network;

import com.mashup.nnaa.network.model.SignUpDto;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserControllerService {

    @POST("user/email/sign_in")
    Call<ResponseBody> signInOrRegEmail(@Body HashMap<String, String> body);

    @POST("user/email")
    Call<SignUpDto> registerEmail(@Body HashMap<String, String> body);

    @POST("user/social")
    Call<ResponseBody> signInOrRegSocial(@Body HashMap<String, String> body);

    @PATCH("user/bookmark/{questionId}")
    Call<ResponseBody> favoriteEnroll(@Path("questionId") String questionId);

    @DELETE("user/bookmark/{questionId}")
    Call<ResponseBody> favoriteDelete(@Path("questionId") String questionId);
}
