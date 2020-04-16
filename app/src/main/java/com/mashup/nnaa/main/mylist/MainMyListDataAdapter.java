package com.mashup.nnaa.main.mylist;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.network.model.InboxQuestionnaireDto;
import com.mashup.nnaa.network.model.OutboxQuestionnaireDto;

import java.util.ArrayList;

public class MainMyListDataAdapter extends RecyclerView.Adapter {
    private ArrayList<InOutBoxQuestionnaireItem> items;

    public MainMyListDataAdapter() {
        items = new ArrayList<>();
    }

    public void setData(ArrayList<InOutBoxQuestionnaireItem> data) {
        this.items = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class InOutBoxQuestionnaireItem {
        public String category;
        public String createdAt;
        public String id;
        public int questionsCount;
        public String receiverId;
        public String receiverName;
        public String senderId;
        public String senderName;

        public static InOutBoxQuestionnaireItem createFromInboxDto(InboxQuestionnaireDto dto) {
            InOutBoxQuestionnaireItem item = new InOutBoxQuestionnaireItem();
            item.category = dto.category;
            item.createdAt = dto.createdAt;
            item.id = dto.id;
            item.questionsCount = dto.questionsCount;
            item.senderId = dto.senderId;
            item.senderName = dto.senderName;
            return item;
        }

        public static InOutBoxQuestionnaireItem createFromOutboxDto(OutboxQuestionnaireDto dto) {
            InOutBoxQuestionnaireItem item = new InOutBoxQuestionnaireItem();
            item.category = dto.category;
            item.createdAt = dto.createdAt;
            item.id = dto.id;
            item.questionsCount = dto.questionsCount;
            item.receiverId = dto.receiverId;
            item.receiverName = dto.receiverName;
            return item;
        }
    }
}
