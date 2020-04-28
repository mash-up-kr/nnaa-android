package com.mashup.nnaa.network;

import com.mashup.nnaa.network.model.InboxQuestionnaireDto;
import com.mashup.nnaa.network.model.OutboxQuestionnaireDto;
import com.mashup.nnaa.network.model.Questionnaire;
import com.mashup.nnaa.network.model.QuestionnaireAnswerDto;
import com.mashup.nnaa.network.model.QuestionnaireDto;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface QuestionnaireControllerService {

    // 질문지 보내기
    @Headers({"Content-Type: application/json"})
    @POST("questionnaire")
    Call<Questionnaire> postQuestionnaire(@Header("id") String id,
                                          @Header("token") String token,
                                          @Body Questionnaire body);

    // 질문지 보기
    @GET("questionnaire/{questionnaireId}")
    Call<QuestionnaireDto> getQuestionnaire(@Path("questionnaireId") String questionnaireId);

    // 질문지에 답변하기
    @Headers({"Content-Type: application/json"})
    @PUT("questionnaire/{questionnaireId")
    Call<Questionnaire> answerQuestionnaire(@Header("id") String id,
                                            @Header("token") String token,
                                            @Path("questionnaireId") String questionnaireId,
                                            @Body Questionnaire questionnaireAnswerDto);

    // 받은 질문지 리스트 보기
    @GET("questionnaire/inbox")
    Call<List<InboxQuestionnaireDto>> getReceiveQuestionnaires();

    // 보낸 질문지 리스트 보기
    @GET("questionnaire/outbox")
    Call<List<OutboxQuestionnaireDto>> getSendQuestionnaires();

}
