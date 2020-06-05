package com.mashup.nnaa.main.notifications;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.NnaaApplication;
import com.mashup.nnaa.R;
import com.mashup.nnaa.data.MainHomeNotificationItem;

import java.util.ArrayList;

public class MainNotificationsAdapter extends RecyclerView.Adapter<MainNotificationsViewHolder> {
    private ArrayList<MainHomeNotificationItem> items  = new ArrayList<>();

    @NonNull
    @Override
    public MainNotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainNotificationsViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_main_notifications, parent, false));    }

    @Override
    public void onBindViewHolder(@NonNull MainNotificationsViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(ArrayList<MainHomeNotificationItem> data){
        if (data == null) {
            data = new ArrayList<>();
        }

        if (data.isEmpty()) {
            data.add(new MainHomeNotificationItem(
                    NnaaApplication.getAppContext().getString(R.string.noti_no_notifications),
                    ""
            ));
        }

        items.clear();
        items.addAll(data);
        notifyDataSetChanged();
    }
}
