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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.LoginActivity;
import com.mashup.nnaa.NnaaApplication;
import com.mashup.nnaa.R;
import com.mashup.nnaa.main.MainSettingFavoritesActivity;
import com.mashup.nnaa.network.model.FriendDto;
import com.mashup.nnaa.util.AccountManager;

import java.util.ArrayList;
import java.util.Objects;

public class MainSettingFragment extends Fragment {

    public static MainSettingFragment newInstance() {
        MainSettingFragment fragment = new MainSettingFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_setting, container, false);

        String userName = AccountManager.getInstance().getUserAuthHeaderInfo().getName();

        TextView tvTitle = view.findViewById(R.id.tv_setting_title);
        tvTitle.setText(String.format(
                getString(R.string.setting_title),
                userName
        ));
        initList(view.findViewById(R.id.rv_settings));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initList(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new MainSettingAdapter());
    }
}
