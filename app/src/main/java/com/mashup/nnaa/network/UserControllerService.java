package com.mashup.nnaa.network;

import com.mashup.nnaa.network.model.FriendDto;
import com.mashup.nnaa.network.model.LoginDto;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.network.model.Questionnaire;
import com.mashup.nnaa.network.model.SignUpDto;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserControllerService {

    // 로그인(이메일)
    @POST("user/email/sign_in")
    Call<LoginDto> signIn(@Body HashMap<String, String> body);

    // 회원가입(이메일)
    @POST("user/email/sign_up")
    Call<SignUpDto> registerEmail(@Body HashMap<String, String> body);

    // 로그인한 사용자의 비밀번호 재설정
    @PUT("user/password")
    Call<ResponseBody> changePw(@Header("id") String id,
                                @Header("token") String token,
                                @Query("currentPassword") String currentPassword,
                                @Query("newPassword") String newPassword,
                                @Query("newPasswordAgain") String newPasswordAgain);

    // 재설정 이메일을 거쳐온 후 비번 재설정
    @PUT("user/email/password")
    Call<ResponseBody> resetPw(@Header("id") String id,
                               @Header("token") String token,
                               @Query("newPassword") String newPassword,
                               @Query("newPasswordAgain") String newPasswordAgain);

    // 비밀번호 재설정 이메일 보내기
    @GET("user/password/email")
    Call<ResponseBody> sendNewPw(@Query("email") String email);

    // sns
    @POST("user/social")
    Call<ResponseBody> signInOrRegSocial(@Body HashMap<String, String> body);

    // 즐겨찾기 해둔 질문들 보여주기
    @GET("user/bookmark")
    Call<ArrayList<NewQuestionDto>> showFavorites(@Header("id") String id,
                                                  @Header("token") String token);

    // 즐겨찾기 등록
    @POST("user/bookmark")
    Call<NewQuestionDto> favoriteEnroll(@Header("id") String id,
                                        @Header("token") String token,
                                        @Body NewQuestionDto body);

    // 즐겨찾기 취소
    @DELETE("user/bookmark/{bookmarkQuestionId}")
    Call<NewQuestionDto> favoriteDelete(@Header("id") String id,
                                        @Header("token") String token,
                                        @Path("bookmarkQuestionId") String bookmarkQuestionId);

    // 이름으로 유저찾기
    @GET("user")
    Call<ArrayList<Questionnaire>> userName(@Query("name") String name);

    // 친구등록
    @POST("user/friend/{id}")
    Call<FriendDto> friendEnroll(@Path("id") String id);
}
