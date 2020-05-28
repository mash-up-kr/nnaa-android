package com.mashup.nnaa.network;

import android.text.TextUtils;

import com.mashup.nnaa.BuildConfig;
import com.mashup.nnaa.main.mylist.MainMyListDataAdapter;
import com.mashup.nnaa.network.model.InboxQuestionnaireDto;
import com.mashup.nnaa.network.model.LoginDto;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.network.model.OutboxQuestionnaireDto;
import com.mashup.nnaa.network.model.Questionnaire;
import com.mashup.nnaa.network.model.QuestionnaireAnswerDto;
import com.mashup.nnaa.network.model.SignUpDto;
import com.mashup.nnaa.util.AccountManager;

import java.util.ArrayList;
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


    public void refreshRetrofit() {
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
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
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
    public void signIn(String email, String encPw, Callback<LoginDto> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<LoginDto> userInfo = service.signIn(
                new HashMap<String, String>() {{
                    put("email", email);
                    put("password", encPw);
                }}
        );
        userInfo.enqueue(callback);
    }

    /**
     * Register new member with given information
     *
     * @param email    Email address
     * @param encPw    Encrypted(Sign-In) password string
     * @param name
     * @param callback
     */

    // 회원가입
    public void registerEmail(String email, String encPw, String name, Callback<SignUpDto> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<SignUpDto> signUpDtoCall = service.registerEmail(
                new HashMap<String, String>() {{
                    put("email", email);
                    put("password", encPw);
                    put("name", name);
                }}
        );
        signUpDtoCall.enqueue(callback);
    }

    // 비밀번호 재설정 이메일 보내기
    public void sendNewPw(String email, Callback<ResponseBody> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<ResponseBody> loginDtoCall = service.sendNewPw(email);
        loginDtoCall.enqueue(callback);
    }

    // 로그인 상태 비번 재설정
    public void changePw(String id, String token, String currentPassword, String newPassword, String newPasswordAgain, Callback<ResponseBody> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<ResponseBody> currentPw = service.changePw(id, token, currentPassword, newPassword, newPasswordAgain);
        currentPw.enqueue(callback);
    }

    // 랜딩 후 비번 재설정
    public void resetPw(String id, String token, String newPw, String newPwConfrim, Callback<ResponseBody> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<ResponseBody> reset = service.resetPw(id, token, newPw, newPwConfrim);
        reset.enqueue(callback);
    }

    // 문제지 첫 기본세팅
    public void getQuestion(String id, String token, String category, String size, Callback<ArrayList<NewQuestionDto>> callback) {
        QuestionControllerService service = retrofit.create(QuestionControllerService.class);
        Call<ArrayList<NewQuestionDto>> getQuestionRandom = service.getQuestion(id, token, category, size);
        getQuestionRandom.enqueue(callback);
    }


    // 즐겨찾기 해둔 질문들 보여주기
    public void showFavorites(String id, String token, Callback<ArrayList<NewQuestionDto>> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<ArrayList<NewQuestionDto>> showFavorites = service.showFavorites(id, token);
        showFavorites.enqueue(callback);
    }

    // 즐겨찾기 등록
    public void favoriteEnroll(String id, String token, NewQuestionDto newQuestionDto, Callback<NewQuestionDto> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<NewQuestionDto> favoriteEnroll = service.favoriteEnroll(id, token, newQuestionDto);
        favoriteEnroll.enqueue(callback);
    }

    // 즐겨찾기 취소
    public void favoriteDelete(String id, String token, String bookmarkQuestionId, Callback<NewQuestionDto> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<NewQuestionDto> favoriteDelete = service.favoriteDelete(id, token, bookmarkQuestionId);
        favoriteDelete.enqueue(callback);
    }

    // 질문지 보내기
    public void postQuestionnaire(String id, String token, Questionnaire questionnaire, Callback<Questionnaire> callback) {
        QuestionnaireControllerService service = retrofit.create(QuestionnaireControllerService.class);
        Call<Questionnaire> postQuestionnaire = service.postQuestionnaire(id, token, questionnaire);
        postQuestionnaire.enqueue(callback);
    }

    // 질문지 답변
    public void answerQuestionnaire(String id, String token, String questionnaireId, QuestionnaireAnswerDto answerDto, Callback<QuestionnaireAnswerDto> callback) {
        QuestionnaireControllerService service = retrofit.create(QuestionnaireControllerService.class);
        Call<QuestionnaireAnswerDto> answerQuestionnaire = service.answerQuestionnaire(id, token, questionnaireId, answerDto);
        answerQuestionnaire.enqueue(callback);
    }

    // 이름으로 유저찾기
    public void userName(String name, Callback<ArrayList<Questionnaire>> callback) {
        UserControllerService service = retrofit.create(UserControllerService.class);
        Call<ArrayList<Questionnaire>> userName = service.userName(name);
        userName.enqueue(callback);
    }

    // 질문지 보기
    public void showQuestionnaire(String id, String token, String questionnaireId, Callback<MainMyListDataAdapter.InOutBoxQuestionnaireItem> callback) {
        QuestionnaireControllerService service = retrofit.create(QuestionnaireControllerService.class);
        Call<MainMyListDataAdapter.InOutBoxQuestionnaireItem> showQuestionnaire = service.showQuestionnaire(id, token, questionnaireId);
        showQuestionnaire.enqueue(callback);
    }

    // 받은 질문지 리스트
    public void getReceivedQuestionnaires(Callback<List<InboxQuestionnaireDto>> callback) {
        QuestionnaireControllerService service = retrofit.create(QuestionnaireControllerService.class);
        Call<List<InboxQuestionnaireDto>> receivedQuestionnaire = service.getReceiveQuestionnaires();
        receivedQuestionnaire.enqueue(callback);
    }

    // 보낸 질문지 리스트
    public void getSendQuestionnaires(Callback<List<OutboxQuestionnaireDto>> callback) {
        QuestionnaireControllerService service = retrofit.create(QuestionnaireControllerService.class);
        Call<List<OutboxQuestionnaireDto>> sendQuestionnaire = service.getSendQuestionnaires();
        sendQuestionnaire.enqueue(callback);
    }
}
