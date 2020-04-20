package com.mashup.nnaa.util;

import android.text.TextUtils;
import android.util.Log;

import com.facebook.login.LoginManager;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.UserAuthHeaderInfo;
import com.mashup.nnaa.network.model.LoginDto;
import com.mashup.nnaa.network.model.SignUpDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountManager {
    public static final String SHARED_PREF_LAST_ACCOUNT_EMAIL = "last_account_email";
    public static final String SHARED_PREF_LAST_ACCOUNT_ENCRYPT_PW = "last_account_pw";
    public static Object ISignInResultListener;

    private static AccountManager instance = new AccountManager();

    public static AccountManager getInstance() {
        return instance;
    }

    private AccountManager() {
    }

    private UserAuthHeaderInfo userAuthHeaderInfo;

    public UserAuthHeaderInfo getUserAuthHeaderInfo() {
        return userAuthHeaderInfo;
    }

    public void setUserAuthHeaderInfo(UserAuthHeaderInfo info) {
        this.userAuthHeaderInfo = info;
    }

    public void setUserAuthHeaderInfo(String userId, String name, String token) {
        this.userAuthHeaderInfo = new UserAuthHeaderInfo(userId, name, token);
    }

    public void executeAutoSignIn(ISignInResultListener resultListener) {
        String lastEmail =
                SharedPrefHelper.getInstance().getString(SHARED_PREF_LAST_ACCOUNT_EMAIL);
        String lastEncPw =
                SharedPrefHelper.getInstance().getString(SHARED_PREF_LAST_ACCOUNT_ENCRYPT_PW);

        executeSignIn(lastEmail, lastEncPw, true, true, new ISignInResultListener() {
            @Override
            public void onSignInSuccess(String id, String name, String token) {
                resultListener.onSignInSuccess(id, name, token);
            }

            @Override
            public void onSignInFail() {
                SharedPrefHelper.getInstance().put(SHARED_PREF_LAST_ACCOUNT_EMAIL, "");
                SharedPrefHelper.getInstance().put(SHARED_PREF_LAST_ACCOUNT_ENCRYPT_PW, "");
                resultListener.onSignInFail();
            }
        });
    }

    // login
    public void executeSignIn(String email, String password,
                              boolean isGivenPwEncryptedLocally,
                              boolean saveForAutoSignIn,
                              ISignInResultListener resultListener) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Log.v("SignIn", "Sign in fail (not enough): " + email);
            resultListener.onSignInFail();
            return;
        }

        if (!isValidEmailAddress(email)) {
            Log.v("SignIn", "Sign in fail (wrong email address): " + email);
            resultListener.onSignInFail();
            return;
        }

        final String pwEncLocal;
        final String pwEncSignIn;

        if (isGivenPwEncryptedLocally) {
            pwEncLocal = password;
            pwEncSignIn = EncryptUtil.encryptPasswordFromLocalToSignIn(pwEncLocal);
        } else {
            pwEncLocal = EncryptUtil.encryptPasswordFromPlaintextToLocal(password);
            pwEncSignIn = EncryptUtil.encryptPasswordFromLocalToSignIn(pwEncLocal);
        }

        RetrofitHelper.getInstance().signIn(email, pwEncSignIn, new Callback<LoginDto>() {
            @Override
            public void onResponse(Call<LoginDto> call, Response<LoginDto> response) {
                String id = response.headers().get("id");
                String token = response.headers().get("token");
                String name = "Somebody";
                LoginDto body = response.body();
                if (body != null)
                    name = body.getName();

                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(token)) {
                    Log.v("SignIn", "Sign in fail (no id or token value received): " + email);
                    RetrofitHelper.getInstance().refreshRetrofit();
                    resultListener.onSignInFail();
                } else {
                    Log.v("SignIn", "Sign in success: " + email + "," + token + "," + name);

                    if (saveForAutoSignIn) {
                        SharedPrefHelper.getInstance()
                                .put(SHARED_PREF_LAST_ACCOUNT_EMAIL, email);
                        SharedPrefHelper.getInstance()
                                .put(SHARED_PREF_LAST_ACCOUNT_ENCRYPT_PW, pwEncLocal);
                    }

                    setUserAuthHeaderInfo(id, name, token);
                    RetrofitHelper.getInstance().refreshRetrofit();
                    resultListener.onSignInSuccess(id, name, token);
                }
            }

            @Override
            public void onFailure(Call<LoginDto> call, Throwable t) {
                Log.v("SignIn", "Sign in fail: " + email);
                RetrofitHelper.getInstance().refreshRetrofit();
                resultListener.onSignInFail();
            }
        });
    }

    // Register
    public void executeRegister(String email, String password, String name,
                                ISignInResultListener resultListener) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Log.v("Register", "Register fail (not enough): " + email);
            resultListener.onSignInFail();
            return;
        }

        if (!isValidEmailAddress(email)) {
            Log.v("Register", "Register fail (wrong email address): " + email);
            resultListener.onSignInFail();
            return;
        }

        String encPasswd = EncryptUtil.encryptPasswordFromPlaintextToSignIn(password);

        RetrofitHelper.getInstance().registerEmail(email, encPasswd, name, new Callback<SignUpDto>() {
            @Override
            public void onResponse(Call<SignUpDto> call, Response<SignUpDto> response) {
                String id = response.headers().get("id");
                String token = response.headers().get("token");
                SignUpDto body = response.body();

                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(token)) {
                    Log.v("Register", "Reigster fail (no id or token value received): " + email);
                    resultListener.onSignInFail();
                } else {
                    Log.v("Register", "Register in success: " + email + "," + name + "," + password);
                    resultListener.onSignInSuccess(id, name, token);
                }
            }

            @Override
            public void onFailure(Call<SignUpDto> call, Throwable t) {
                Log.v("Register", "Register in fail: " + t.getMessage());

            }
        });
    }

    // sign out
    public void executeSignOut(ISignOutResultListener listener) {
        SharedPrefHelper.getInstance().put(SHARED_PREF_LAST_ACCOUNT_EMAIL, "");
        SharedPrefHelper.getInstance().put(SHARED_PREF_LAST_ACCOUNT_ENCRYPT_PW, "");
        userAuthHeaderInfo = null;
        Log.v("SignOut", "Signed out");
        RetrofitHelper.getInstance().refreshRetrofit();
        listener.onSignOut();
        LoginManager.getInstance().logOut();
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public interface ISignInResultListener {
        void onSignInSuccess(String id, String name, String token);

        void onSignInFail();
    }

    public interface ISignOutResultListener {
        void onSignOut();
    }
}