package com.mashup.nnaa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.mashup.nnaa.main.MainActivity;
import com.mashup.nnaa.main.home.MainHomeFragment;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.SharedPrefHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";
    private SessionCallback callback; // 카카오 콜백
    private Button btn_facebook_login, btn_login;
    private CheckBox autoLogin;
    private EditText edit_email, edit_password;
    private TextView txt_register, txt_forget_password;
    private CallbackManager mCallbackManager; // facebook 콜백
    private SharedPreferences setting;
    private SharedPreferences.Editor editor;

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
        autoLogin = findViewById(R.id.auto_login);


        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();

        if (setting.getBoolean("auto_login", false)) {
            edit_email.setText(setting.getString("ID", ""));
            edit_password.setText(setting.getString("PW", ""));
            autoLogin.setChecked(true);
        }
        // auto login
        autoLogin.setOnClickListener(view -> {
            if (autoLogin.isChecked()) {
                Toast.makeText(this, "자동 로그인", Toast.LENGTH_SHORT).show();
                String ID = edit_email.getText().toString();
                String PW = edit_password.getText().toString();
                editor.putString("ID", ID);
                editor.putString("PW", PW);
                editor.putBoolean("auto_login", true);
                editor.apply();
            } else {
                editor.clear();
                editor.apply();
            }
        });

        //email login
        btn_login.setOnClickListener(view -> AccountManager.getInstance().executeSignIn(
                edit_email.getText().toString(),
                edit_password.getText().toString(),
                false, false,
                new AccountManager.ISignInResultListener() {
                    @Override
                    public void onSignInSuccess(String id, String name, String token) {
                        launchMainActivity();
                        Log.d(TAG, "id: " + id);
                    }

                    @Override
                    public void onSignInFail() {
                        Log.v(TAG, "Fail to login");
                        edit_email.setText("");
                        edit_password.setText("");
                        Toast.makeText(LoginActivity.this, "(로그인 실패) 이메일 또는 비밀번호를 다시 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                }));


        //email signIn
        txt_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


        // 비밀번호 찾기
        txt_forget_password.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, FindPwActivity.class);
            startActivity(intent);
        });

        //kakao login
        kakaoData();

        //facebook login
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v(TAG, "facebook login success");
                GraphRequest request = new GraphRequest().newMeRequest(loginResult.getAccessToken(), (object, response) -> {
                    try {
                        String name = object.getString("name");
                        Intent facbook_intent = new Intent(getBaseContext(), MainActivity.class);
                        facbook_intent.putExtra("facebook", name);
                        startActivity(facbook_intent);
                        Log.v(TAG, "facebook on success:" + object.getString("email") + object.getString("name"));
                        Log.v(TAG, "facebook token" + loginResult.getAccessToken().getToken());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.v(TAG, "facebook login cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.v(TAG, "facebook login error");
            }
        });
    }

    /**
     * 카카오톡
     **/
    private void kakaoData() {
        // 카카오 로그인 콜백받기
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);

/** 토큰 만료시 갱신을 시켜준다**/
        if (Session.getCurrentSession().isOpenable()) {
            Session.getCurrentSession().checkAndImplicitOpen();
        }

        Log.v(TAG, "토큰큰 : " + Session.getCurrentSession().getTokenInfo().getAccessToken());
        Log.v(TAG, "토큰큰 리프레쉬토큰 : " + Session.getCurrentSession().getTokenInfo().getRefreshToken());
        Log.v(TAG, "토큰큰 파이어데이트 : " + Session.getCurrentSession().getTokenInfo().getRemainingExpireTime());
    }


    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.v(TAG, "카카오 로그인 성공 ");
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.e(TAG, "exception : " + exception);
            }
            setContentView(R.layout.activity_login);
        }
    }

    // 카카오 유저의 정보 받아온다 (이메일 ,id, 이름)
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
                Log.e(TAG, "requestMe onSuccess message : " + result.getKakaoAccount().getEmail() + " " + result.getId() + " " + result.getNickname() );
                String name = result.getNickname();
                Intent kakao_intent = new Intent(getBaseContext(), MainActivity.class);
                kakao_intent.putExtra("kakao", name);
                startActivity(kakao_intent);
                //launchMainActivity();
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        Log.v("페이스북 로그인", "resultcode->" + resultCode);
    }

    private void launchMainActivity() {
        Toast.makeText(getBaseContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}


