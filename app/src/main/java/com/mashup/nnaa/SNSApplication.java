package com.mashup.nnaa;

import android.app.Application;

import com.kakao.auth.KakaoSDK;

public class SNSApplication extends Application {

    private static SNSApplication instance;

    public static SNSApplication getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new SNSApplication();
            return instance;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        KakaoSDK.init(new KaKaoSDKAdapter());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}

