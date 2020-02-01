package com.mashup.nnaa.main.setting;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;

public class MainSettingViewHolder extends RecyclerView.ViewHolder {
    private TextView tvTitle;
    public MainSettingViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.tv_item_name);
    }

    public void bind(String name) {
        tvTitle.setText(name);
    }
}
