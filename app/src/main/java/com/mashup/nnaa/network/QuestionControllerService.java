package com.mashup.nnaa.network;

import com.google.gson.JsonObject;
import com.mashup.nnaa.network.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QuestionControllerService {

    @GET("question?category={category}&type={type}")
    Call<List<QuestionDto>> getPresetQuestions(@Path("category") String category,
                                               @Path("type") String type);

    // 문제지 첫 기본세팅
    @Headers({
            "id:5e42f1aa611ae143326c6722",
            "token:98caf4e29f4330ae0dabbc95057c154b8ff0ab068d3e9c99fba8b9f7cdd0e701"
    })
    @GET("question/random")
    //Call<List<Question>> getQuestion();
    Call<List<NewQuestionDto>> getQuestion(@Query("category") String category, @Query("size") Integer size);

    // 직접 질문 입력해서 질문 추가하기
    @POST("question")
    Call<NewQuestionDto> postQuestion(@Body NewQuestionDto body);

}
