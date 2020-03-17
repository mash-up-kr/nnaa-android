package com.mashup.nnaa.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.mashup.nnaa.NnaaApplication;

public class SharedPrefHelper {
    private static SharedPrefHelper instance = new SharedPrefHelper();
    public static SharedPrefHelper getInstance() { return instance; }

    private final static String PREF_KEY = "NNAA_SHARED_PREF";

    private SharedPreferences getShPref() {
        return NnaaApplication
                .getAppContext()
                .getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
    }

    public void put(String key, int value) {
        getShPref().edit().putInt(key, value).apply();
    }

    public void put(String key, float value) {
        getShPref().edit().putFloat(key, value).apply();
    }

    public void put(String key, boolean value) {
        getShPref().edit().putBoolean(key, value).apply();
    }

    public void put(String key, String value) {
        getShPref().edit().putString(key, value).apply();
    }

    public int getInt(String key, int defaultValue) {
        return getShPref().getInt(key, defaultValue);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public float getFloat(String key, float defaultValue) {
        return getShPref().getFloat(key, defaultValue);
    }

    public float getFloat(String key) {
        return getFloat(key, 0f);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getShPref().getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public String getString(String key, String defaultValue) {
        return getShPref().getString(key, defaultValue);
    }

    public String getString(String key) {
        return getString(key, "");
    }
}
