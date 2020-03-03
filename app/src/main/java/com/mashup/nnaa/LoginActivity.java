package com.mashup.nnaa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.mashup.nnaa.main.MainActivity;
import com.mashup.nnaa.util.AccountManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class LoginActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";
    private SessionCallback callback;

    private Button btn_facebook_login, btn_facebook_logout, btn_login;
    private EditText edit_email, edit_password;
    private TextView txt_register, txt_forget_password;

    private LoginCallback mLoginCallback;

    private CallbackManager mCallbackManager;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        txt_register = findViewById(R.id.txt_register);
        txt_forget_password = findViewById(R.id.txt_forget_password);
        btn_login = findViewById(R.id.btn_login);
        btn_facebook_login = findViewById(R.id.btn_facebook_login);
        btn_facebook_logout = findViewById(R.id.btn_facebook_logout);

        //email login
        btn_login.setOnClickListener(view -> AccountManager.getInstance().executeSignIn(
                edit_email.getText().toString(),
                edit_password.getText().toString(),
                false, false,
                new AccountManager.ISignInResultListener() {
                    @Override
                    public void onSignInSuccess(String id, String token) {
                        launchMainActivity();
                        Log.d(TAG,"id: " + id);
                    }

                    @Override
                    public void onSignInFail() {
                        Log.d(TAG, "Fail to login");
                    }
                }));

        //email signIn
        txt_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        //kakao login
         kakaoData();

        Context mContext = getApplicationContext();

        //facebook login
        mCallbackManager = CallbackManager.Factory.create();

        mLoginCallback = new LoginCallback();

        btn_facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginManager loginManager = LoginManager.getInstance();

                loginManager.logInWithReadPermissions(LoginActivity.this,

                        Arrays.asList("public_profile", "email"));

                loginManager.registerCallback(mCallbackManager, mLoginCallback);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });

        btn_facebook_logout.setOnClickListener(view -> LoginManager.getInstance().logOut());
    }

    /**
     * 카카오톡
     **/
    private void kakaoData() {
        findViewById(R.id.kakaoLogout).setOnClickListener(view -> onClickLogout());

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);

/** 토큰 만료시 갱신을 시켜준다**/
        if (Session.getCurrentSession().isOpenable()) {
            Session.getCurrentSession().checkAndImplicitOpen();
        }

        Log.e(TAG, "토큰큰 : " + Session.getCurrentSession().getTokenInfo().getAccessToken());
        Log.e(TAG, "토큰큰 리프레쉬토큰 : " + Session.getCurrentSession().getTokenInfo().getRefreshToken());
        Log.e(TAG, "토큰큰 파이어데이트 : " + Session.getCurrentSession().getTokenInfo().getRemainingExpireTime());
    }


    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.e(TAG, "카카오 로그인 성공 ");
            requestMe();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.e(TAG, "exception : " + exception);
            }
        }
    }

    /**
     * 사용자에 대한 정보를 가져온다
     **/
    private void requestMe() {

        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                super.onFailure(errorResult);
                Log.e(TAG, "requestMe onFailure message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onFailureForUiThread(ErrorResult errorResult) {
                super.onFailureForUiThread(errorResult);
                Log.e(TAG, "requestMe onFailureForUiThread message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e(TAG, "requestMe onSessionClosed message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onSuccess(MeV2Response result) {
                Log.e(TAG, "requestMe onSuccess message : " + result.getKakaoAccount().getEmail() + " " + result.getId() + " " + result.getNickname());
            }
        });
    }

    /**
     * 로그아웃시
     **/
    private void onClickLogout() {

        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e(TAG, "카카오 로그아웃 onSessionClosed");

            }

            @Override
            public void onNotSignedUp() {
                Log.e(TAG, "카카오 로그아웃 onNotSignedUp");
            }

            @Override
            public void onSuccess(Long result) {
                Log.e(TAG, "카카오 로그아웃 onSuccess");
            }
        });
    }

    private void launchMainActivity() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}


