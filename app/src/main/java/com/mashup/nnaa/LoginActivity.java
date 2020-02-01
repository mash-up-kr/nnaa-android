package com.mashup.nnaa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    //구글 로그인 뺴자
    public static final int RC_SIGN_IN = 1;

    private GoogleApiClient mGoogleApiClient;

    private String TAG = "LoginActivity";
    private SessionCallback callback;

    private Context mContext;


    private Button btn_facebook_login, btn_facebook_logout, btn_google_logout;

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

        btn_google_logout = findViewById(R.id.btn_google_logout);

        //google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        //  mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        SignInButton google_login = findViewById(R.id.google_login);

        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signIntent, RC_SIGN_IN);

            }
        });


        //kakao login
        kakaoData();

        mContext = getApplicationContext();

        //facebook login
        mCallbackManager = CallbackManager.Factory.create();

        mLoginCallback = new LoginCallback();

        btn_facebook_login = findViewById(R.id.btn_facebook_login);
        btn_facebook_logout = findViewById(R.id.btn_facebook_logout);

        btn_facebook_login.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                LoginManager loginManager = LoginManager.getInstance();

                loginManager.logInWithReadPermissions(LoginActivity.this,

                        Arrays.asList("public_profile", "email"));

                loginManager.registerCallback(mCallbackManager, mLoginCallback);

                Intent intent = new Intent(LoginActivity.this, SetTypeOfFriendActivity.class);
                startActivity(intent);

            }
        });

        btn_facebook_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
            }
        });
    }


    /**
     * 카카오톡
     **/
    private void kakaoData() {
        findViewById(R.id.kakaoLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogout();
            }
        });

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

            Intent intent = new Intent(LoginActivity.this, SetTypeOfFriendActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Intent intent = new Intent(LoginActivity.this, SetTypeOfFriendActivity.class);
            startActivity(intent);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();


            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String email = account.getEmail();
            String m = account.getFamilyName();
            String m2 = account.getGivenName();
            String m3 = account.getDisplayName();
            Log.d("Name : ", m);
            Log.d("Name2:", m2);
            Log.d("Name3:", m3);
            Log.d("Email: ", email);

        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}


