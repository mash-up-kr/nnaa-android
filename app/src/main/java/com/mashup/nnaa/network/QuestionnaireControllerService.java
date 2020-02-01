package com.mashup.nnaa.network;

import com.mashup.nnaa.network.model.Questionnaire;
import com.mashup.nnaa.network.model.QuestionnaireAnswerDto;
import com.mashup.nnaa.network.model.QuestionnaireDto;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface QuestionnaireControllerService {

    @POST("questionnaire")
    Call<Questionnaire> postQuestionnaire(@Body HashMap<String, String> body);

    @GET("questionnaire/{questionnaireId}")
    Call<QuestionnaireDto> getQuestionnaire(@Path("questionnaireId") String questionnaireId);

    @PUT("questionnaire/{questionnaireId")
    Call<Questionnaire> answerQuestionnaire(@Path("questionnaireId") String questionnaireId,
                                            QuestionnaireAnswerDto questionnaireAnswerDto);

    @GET("questionnaire/inbox")
    Call<List<QuestionnaireDto>> getReceiveQuestionnaires();

    @GET("questionnaire/outbox")
    Call<List<QuestionnaireDto>> getSendQuestionnaires();

}
