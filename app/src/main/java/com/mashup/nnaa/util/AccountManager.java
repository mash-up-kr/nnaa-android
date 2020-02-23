package com.mashup.nnaa.util;

import android.text.TextUtils;
import android.util.Log;

import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.UserAuthHeaderInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountManager {
    public static final String SHARED_PREF_LAST_ACCOUNT_EMAIL = "last_account_email";
    public static final String SHARED_PREF_LAST_ACCOUNT_ENCRYPT_PW = "last_account_pw";
    public static Object ISignInResultListener;

    private static AccountManager instance = new AccountManager();
    public static AccountManager getInstance() { return instance; }
    private AccountManager() {}

    private UserAuthHeaderInfo userAuthHeaderInfo;
    public UserAuthHeaderInfo getUserAuthHeaderInfo() { return userAuthHeaderInfo; }

    public void setUserAuthHeaderInfo(UserAuthHeaderInfo info) {
        this.userAuthHeaderInfo = info;
    }

    public void setUserAuthHeaderInfo(String userId, String token) {
        this.userAuthHeaderInfo = new UserAuthHeaderInfo(userId, token);
    }

    public void executeAutoSignIn(ISignInResultListener resultListener) {
        String lastEmail =
                SharedPrefHelper.getInstance().getString(SHARED_PREF_LAST_ACCOUNT_EMAIL);
        String lastEncPw =
                SharedPrefHelper.getInstance().getString(SHARED_PREF_LAST_ACCOUNT_ENCRYPT_PW);

        executeSignIn(lastEmail, lastEncPw,true, true, new ISignInResultListener() {
            @Override
            public void onSignInSuccess(String id, String token) {
                setUserAuthHeaderInfo(id, token);
                resultListener.onSignInSuccess(id, token);
            }

            @Override
            public void onSignInFail() {
                SharedPrefHelper.getInstance().put(SHARED_PREF_LAST_ACCOUNT_EMAIL, "");
                SharedPrefHelper.getInstance().put(SHARED_PREF_LAST_ACCOUNT_ENCRYPT_PW, "");
                resultListener.onSignInFail();
            }
        });
    }

    public void executeSignIn(String email, String pw,
                              boolean isGivenPwEncrypted,
                              boolean saveForAutoSignIn,
                              ISignInResultListener resultListener) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pw)) {
            Log.v("SignIn", "Sign in fail (not enough): " + email);
            resultListener.onSignInFail();
            return;
        }

        if (!isValidEmailAddress(email)) {
            Log.v("SignIn", "Sign in fail (wrong email address): " + email);
            resultListener.onSignInFail();
            return;
        }

        final String pwEnc;
        final String pwForSignIn;

        if (isGivenPwEncrypted) {
            pwEnc = pw;
        } else {
            pwEnc = EncryptUtil.encrypt(pw);
        }
        pwForSignIn = EncryptUtil.doubleEncryptForSignIn(pwEnc);


        RetrofitHelper.getInstance().signInOrRegEmail(email, pwForSignIn, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String id = response.headers().get("id");
                String token = response.headers().get("token");
                ResponseBody body = response.body();

                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(token)) {
                    Log.v("SignIn", "Sign in fail (no id or token value received): " + email);
                    resultListener.onSignInFail();
                } else {
                    Log.v("SignIn", "Sign in success: " + email);

                    if (saveForAutoSignIn) {
                        SharedPrefHelper.getInstance()
                                .put(SHARED_PREF_LAST_ACCOUNT_EMAIL, email);
                        SharedPrefHelper.getInstance()
                                .put(SHARED_PREF_LAST_ACCOUNT_ENCRYPT_PW, EncryptUtil.encrypt(pwEnc));
                    }

                    resultListener.onSignInSuccess(id, token);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("SignIn", "Sign in fail: " + email);
                resultListener.onSignInFail();
            }
        });
    }

    public void executeSignOut(ISignOutResultListener listener) {
        SharedPrefHelper.getInstance().put(SHARED_PREF_LAST_ACCOUNT_EMAIL, "");
        SharedPrefHelper.getInstance().put(SHARED_PREF_LAST_ACCOUNT_ENCRYPT_PW, "");
        userAuthHeaderInfo = null;
        Log.v("SignOut", "Signed out");
        listener.onSignOut();
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public interface ISignInResultListener {
        void onSignInSuccess(String id, String token);
        void onSignInFail();
    }
    public interface ISignOutResultListener {
        void onSignOut();
    }
}
