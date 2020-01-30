package com.mashup.nnaa.main.setting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.NnaaApplication;
import com.mashup.nnaa.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MainSettingAdapter extends RecyclerView.Adapter<MainSettingViewHolder> {
    private ArrayList<String> items;

    public MainSettingAdapter() {
        String[] itemArr =
                NnaaApplication.getAppContext().getResources()
                        .getStringArray(R.array.settings);
        items = new ArrayList<>(Arrays.asList(itemArr));
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
