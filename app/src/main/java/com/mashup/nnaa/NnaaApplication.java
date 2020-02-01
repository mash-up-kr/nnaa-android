package com.mashup.nnaa;

import android.app.Application;
import android.content.Context;

import com.kakao.auth.KakaoSDK;

public class NnaaApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        KakaoSDK.init(new KaKaoSDKAdapter());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        appContext = null;
    }

    public static Context getAppContext() {
        return appContext;
    }
}
