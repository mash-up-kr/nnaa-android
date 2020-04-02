package com.mashup.nnaa;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginCallback implements FacebookCallback<LoginResult> {


    // 로그인 성공 시 호출 됩니다. Access Token 발급 성공.
    @Override
    public void onSuccess(LoginResult loginResult) {

        Log.v("Callback :: ", "onSuccess");

        requestMe(loginResult.getAccessToken());
        Log.v("@@@@@@", "페이스북 토큰->" + loginResult.getAccessToken().getToken());

    }


    // 로그인 창을 닫을 경우, 호출됩니다.
    @Override
    public void onCancel() {

        Log.v("Callback :: ", "onCancel");
    }


    // 로그인 실패 시에 호출됩니다.

    @Override

    public void onError(FacebookException error) {

        Log.v("Callback :: ", "onError : " + error.getMessage());

    }


    // 사용자 정보 요청
    public void requestMe(AccessToken token) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(token,
                (object, response) -> {
                    Log.v("페이스북 로그인결과", "페이스북 로그인 결과" + response.toString());
                    try {
                        String email = object.getString("email");
                        String name = object.getString("name");

                        Log.v("페이스북 이메일", "eamil->" + email);
                        Log.v("페이스북 이름", "이름->" + name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

    }
}