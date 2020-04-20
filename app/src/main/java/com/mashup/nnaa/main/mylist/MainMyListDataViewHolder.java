package com.mashup.nnaa.main.mylist;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;

public class MainMyListDataViewHolder extends RecyclerView.ViewHolder {
    private MainMyListPagerAdapter.MyListType type;
    private TextView tvFriend;
    private TextView tvTitle;
    private TextView tvCategory;
    private TextView tvDate;
    public MainMyListDataViewHolder(@NonNull View itemView, MainMyListPagerAdapter.MyListType type) {
        super(itemView);
        this.type = type;

        tvFriend = itemView.findViewById(R.id.tv_mylist_friend_name);
        tvTitle = itemView.findViewById(R.id.tv_mylist_title);
        tvCategory = itemView.findViewById(R.id.tv_mylist_category);
        tvDate = itemView.findViewById(R.id.tv_mylist_datetime);

        itemView.setOnClickListener(v -> {
            //Todo : Jump to the
        });
    }

    public void bind(MainMyListDataAdapter.InOutBoxQuestionnaireItem item) {
        String friendName;
        switch (type) {
            case SENT:
                friendName = item.receiverName;
                break;
            case RECEIVED:
                friendName = item.senderName;
                break;
            default:
                friendName = TextUtils.isEmpty(item.senderName) ? item.receiverName : item.senderName;
        }

        tvFriend.setText(friendName);
        tvTitle.setText(item.id + "/" + item.questionsCount);
        tvCategory.setText(item.category);
        tvDate.setText(item.createdAt);
    }
}
