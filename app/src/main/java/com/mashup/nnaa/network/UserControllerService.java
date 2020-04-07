package com.mashup.nnaa.network;

import com.mashup.nnaa.network.model.LoginDto;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.network.model.Question;
import com.mashup.nnaa.network.model.SignUpDto;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserControllerService {

    // 로그인(이메일)
    @POST("user/email/sign_in")
    Call<LoginDto> signInOrRegEmail(@Body HashMap<String, String> body);

    // 회원가입(이메일)
    @POST("user/email")
    Call<SignUpDto> registerEmail(@Body HashMap<String, String> body);

    @POST("user/social")
    Call<ResponseBody> signInOrRegSocial(@Body HashMap<String, String> body);

    // 즐겨찾기 해둔 질문들 보여주기
    @Headers({
            "id:5e42f1aa611ae143326c6722",
            "token:98caf4e29f4330ae0dabbc95057c154b8ff0ab068d3e9c99fba8b9f7cdd0e701"
    })
    @GET("user/bookmark")
    Call<List<NewQuestionDto>> showFavorites();

    @Headers({
            "id:5e42f1aa611ae143326c6722",
            "token:98caf4e29f4330ae0dabbc95057c154b8ff0ab068d3e9c99fba8b9f7cdd0e701"
    })
    // 즐겨찾기 등록
    @PATCH("user/bookmark/{questionId}")
    Call<NewQuestionDto> favoriteEnroll(@Path("questionId") String questionId);

    // 즐겨찾기 취소
    @DELETE("user/bookmark/{questionId}")
    Call<NewQuestionDto> favoriteDelete(@Path("questionId") String questionId);

    // 이름으로 유저찾기
    @GET("user")
    Call<ResponseBody> userName(@Query("name") String name);
}
