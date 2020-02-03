package com.mashup.nnaa.main.notifications;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.NnaaApplication;
import com.mashup.nnaa.R;
import com.mashup.nnaa.data.MainHomeNotificationItem;

public class MainNotificationsViewHolder extends RecyclerView.ViewHolder {
    private TextView tvMessage;
    private String link;

    public MainNotificationsViewHolder(@NonNull View itemView) {
        super(itemView);

        tvMessage = itemView.findViewById(R.id.tv_notifications);
        itemView.setOnClickListener(v -> {
            if (link.isEmpty())
                return;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            NnaaApplication.getAppContext().startActivity(intent);
        });
    }

    public void bind(MainHomeNotificationItem item) {
        tvMessage.setText(item.getMessage());
        link = item.getLink();
    }
}
