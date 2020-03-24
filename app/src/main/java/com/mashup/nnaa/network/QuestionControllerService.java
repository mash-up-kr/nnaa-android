package com.mashup.nnaa.network;

import com.mashup.nnaa.network.model.*;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QuestionControllerService {

    @GET("question?category={category}&type={type}")
    Call<List<QuestionDto>> getPresetQuestions(@Path("category") String category,
                                               @Path("type") String type);

    // 문제지 첫 기본세팅
    @GET("question/random")
    Call<List<Question>> getQuestion();

    // 직접 질문 입력해서 질문 추가하기
    @POST("question")
    Call<NewQuestionDto> postQuestion(@Body HashMap<String, String> body);



}
