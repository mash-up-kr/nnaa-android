package com.mashup.nnaa.network;

import android.text.TextUtils;

import com.mashup.nnaa.BuildConfig;
import com.mashup.nnaa.network.model.Question;
import com.mashup.nnaa.network.model.QuestionnaireDto;
import com.mashup.nnaa.network.model.SignUpDto;
import com.mashup.nnaa.util.AccountManager;

import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private Retrofit retrofit;
    public final String ENTRY_URL = "https://thisisyourbackend.kr/";

    // singleton
    private static final RetrofitHelper _instance = new RetrofitHelper();

    public static RetrofitHelper getInstance() {
        return _instance;
    }


    private RetrofitHelper() {
        refreshRetrofit();
    }

    private void refreshRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ENTRY_URL)
                .addConverterFactory(GsonConverterFactory.create());

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(
                getUserAuthHeaderInterceptor(AccountManager.getInstance().getUserAuthHeaderInfo())
        );

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            clientBuilder.addInterceptor(loggingInterceptor);
        }

        builder.client(clientBuilder.build());
        retrofit = builder.build();
    }

    private Interceptor getUserAuthHeaderInterceptor(UserAuthHeaderInfo userAuthHeaderInfo) {
        return chain -> {
            Request.Builder newReqBuilder = chain.request().newBuilder();
            if (userAuthHeaderInfo != null
                    && !TextUtils.isEmpty(userAuthHeaderInfo.getToken())
                    && !TextUtils.isEmpty(userAuthHeaderInfo.getUserId())) {
                newReqBuilder
                        .addHeader("id", userAuthHeaderInfo.getUserId())
                        .addHeader("token", userAuthHeaderInfo.getToken());
            }

            return chain.proceed(newReqBuilder.build());
        };
    }

    // 로그인
    public void signInOrRegEmail(String email, String encPw, Callback<ResponseBody> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<ResponseBody> userInfo = service.signInOrRegEmail(
                new HashMap<String, String>() {{
                    put("email", email);
                    put("password", encPw);
                }}
        );
        userInfo.enqueue(callback);
    }

    // 회원가입
    public void registerEmail(String email, String password, String name, Callback<SignUpDto> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<SignUpDto> signUpDtoCall = service.registerEmail(
                new HashMap<String, String>() {{
                    put("email", email);
                    put("password", password);
                    put("name", name);
                }}
        );
        signUpDtoCall.enqueue(callback);
    }

    // 문제지 첫 기본세팅
    public QuestionControllerService getQuestion(Callback<List<Question>> callback) {
        QuestionControllerService service = retrofit.create(QuestionControllerService.class);
        Call<List<Question>> getQuestionRandom = service.getQuestion();
        getQuestionRandom.enqueue(callback);
        return service;
    }

    public void getReceivedQuestionnaire(Callback<List<QuestionnaireDto>> callback) {
        QuestionnaireControllerService service = retrofit.create(QuestionnaireControllerService.class);
        Call<List<QuestionnaireDto>> receivedQuestionnaire = service.getReceiveQuestionnaires();
        receivedQuestionnaire.enqueue(callback);
    }

    public void getSendQuestionnaire(Callback<List<QuestionnaireDto>> callback) {
        QuestionnaireControllerService service = retrofit.create(QuestionnaireControllerService.class);
        Call<List<QuestionnaireDto>> sendQuestionnaire = service.getSendQuestionnaires();
        sendQuestionnaire.enqueue(callback);
    }
}
