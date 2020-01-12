package com.mashup.nnaa.network;

import com.mashup.nnaa.BuildConfig;
import com.mashup.nnaa.network.model.QuestionnaireDto;
import com.mashup.nnaa.network.model.UserInfo;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private Retrofit retrofit;
    public final String ENTRY_URL = "http://52.78.191.221:8080/";

    // singleton
    private static final RetrofitHelper _instance = new RetrofitHelper();
    public static RetrofitHelper getInstance() { return _instance; }

    private RetrofitHelper() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ENTRY_URL)
                .addConverterFactory(GsonConverterFactory.create());

        // add interceptor on DEBUG mode
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            builder.client(client);
        }

        retrofit = builder.build();
    }

    public void getUserInfo(Callback<UserInfo> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<UserInfo> userInfo = service.getUserInfo();
        userInfo.enqueue(callback);
    }

    public void getReceivedQuestionnaire(Callback<List<QuestionnaireDto>> callback) {
        QuestionnaireControllerService service = retrofit.create(QuestionnaireControllerService.class);
        Call<List<QuestionnaireDto>> receivedQuestionnaire = service.getReceiveQuestionnaires();
        receivedQuestionnaire.enqueue(callback);
    }
}
