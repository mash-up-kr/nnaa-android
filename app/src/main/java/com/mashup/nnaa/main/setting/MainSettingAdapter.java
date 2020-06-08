package com.mashup.nnaa.main.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.LoginActivity;
import com.mashup.nnaa.NnaaApplication;
import com.mashup.nnaa.R;
import com.mashup.nnaa.main.MainSettingFavoritesActivity;
import com.mashup.nnaa.util.AccountManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainSettingAdapter extends RecyclerView.Adapter<MainSettingViewHolder> {

    public enum SettingList {
        SIGN_OUT(R.string.setting_sign_out),
        HOW_USE(R.string.setting_how_use),
        MANAGE_FAVORITES(R.string.setting_manage_favorites),
        MY_NNAA(R.string.setting_my_nnaa),
        CHANGE_PW(R.string.setting_change_pw);

        private int textResId;

        public String getText() {
            return NnaaApplication.getAppContext().getString(textResId);
        }

        SettingList(int textResId) {
            this.textResId = textResId;
        }
    }

    private ArrayList<SettingList> items = new ArrayList<>(Arrays.asList(SettingList.values()));

    public MainSettingAdapter() {
    }

    @NonNull
    @Override
    public MainSettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_setting, parent, false);
        return new MainSettingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainSettingViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
