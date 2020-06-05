package com.mashup.nnaa.network;

import com.mashup.nnaa.network.model.NewQuestionDto;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface QuestionControllerService {

    // 문제지 첫 기본세팅
    @GET("question/random")
    Call<ArrayList<NewQuestionDto>> getQuestion(@Header("id") String id,
                                                @Header("token") String token,
                                                @Query("category") String category,
                                                @Query("size") String size);

}
