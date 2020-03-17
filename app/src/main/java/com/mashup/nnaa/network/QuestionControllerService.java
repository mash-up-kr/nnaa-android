package com.mashup.nnaa.network;

import com.mashup.nnaa.network.model.*;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QuestionControllerService {

    @GET("question?category={category}&type={type}")
    Call<List<QuestionDto>> getPresetQuestions(@Path("category") String category,
                                               @Path("type") String type);

    @POST("question")
    Call<Question> postQuestion(@Body HashMap<String, String> body);

    @GET("question/bookmark")
    Call<List<Question>> getBookmarkQuestions();

    @POST("question/bookmark/{questionId}")
    Call<ResponseBody> postBookmarkQuestion(@Path("questionId") String questionId);

    @DELETE("question/bookmark/{questionId}")
    Call<ResponseBody> deleteBookmarkQuestion(@Path("questionId") String questionId);

}
