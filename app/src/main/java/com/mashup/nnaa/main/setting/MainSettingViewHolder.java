package com.mashup.nnaa.main.setting;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.LoginActivity;
import com.mashup.nnaa.NnaaApplication;
import com.mashup.nnaa.R;
import com.mashup.nnaa.main.MainSettingFavoritesActivity;
import com.mashup.nnaa.util.AccountManager;

public class MainSettingViewHolder extends RecyclerView.ViewHolder {
    private TextView tvTitle;
    private MainSettingAdapter.SettingList setting;

    public MainSettingViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.tv_item_name);
        itemView.setOnClickListener(v -> {
            callSettingActivity();
        });
    }

    public void bind(MainSettingAdapter.SettingList setting) {
        this.setting = setting;
        tvTitle.setText(setting.getText());
    }

    private void callSettingActivity() {
        if (setting == null)
            return;

        switch (setting) {
            case SIGN_OUT:
                callSignOut();
                break;
            case HOW_USE:
                callHowtoUse();
                break;
            case MANAGE_FAVORITES:
                callFavoritesManagement();
                break;
            case CHANGE_PW:
                callChagnePw();
                break;
        }

    }

    private void callSignOut() {
        new AlertDialog.Builder(itemView.getContext())
                .setCancelable(true)
                .setTitle(R.string.setting_want_to_sign_out)
                .setPositiveButton(R.string.common_yes, (dialog, which) ->
                        AccountManager.getInstance().executeSignOut(() -> {
                            Intent intent = new Intent(NnaaApplication.getAppContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            NnaaApplication.getAppContext().startActivity(intent);
                        })
                )
                .setNegativeButton(R.string.common_no, null)
                .show();
    }
    private void callHowtoUse() {
        Intent intent = new Intent(NnaaApplication.getAppContext(), HowUseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        NnaaApplication.getAppContext().startActivity(intent);
    }

    private void callFavoritesManagement() {
        Intent intent = new Intent(NnaaApplication.getAppContext(), MainSettingFavoritesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        NnaaApplication.getAppContext().startActivity(intent);
    }

    private void callChagnePw() {
        Intent intent = new Intent(NnaaApplication.getAppContext(), ChangePwActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        NnaaApplication.getAppContext().startActivity(intent);
    }
}
