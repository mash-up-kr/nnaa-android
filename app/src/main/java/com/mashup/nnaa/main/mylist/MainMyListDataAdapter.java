package com.mashup.nnaa.main.mylist;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.InboxQuestionnaireDto;
import com.mashup.nnaa.network.model.OutboxQuestionnaireDto;
import com.mashup.nnaa.util.AccountManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMyListDataAdapter extends RecyclerView.Adapter<MainMyListDataAdapter.MainMyListDataViewHolder> {
    private ArrayList<InOutBoxQuestionnaireItem> items;
    private MainMyListPagerAdapter.MyListType type;
    private String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
    private String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();
    private MainMyListSubFragment mContext;

    public MainMyListDataAdapter(MainMyListPagerAdapter.MyListType type) {
        items = new ArrayList<>();
        this.type = type;
    }

    public void setData(ArrayList<InOutBoxQuestionnaireItem> data, MainMyListSubFragment context) {
        this.items = data;
        this.mContext = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainMyListDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainMyListDataViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_main_mylist_sublist_item, parent, false),
                type);
    }

    @Override
    public void onBindViewHolder(@NonNull MainMyListDataViewHolder holder, int position) {
        holder.bind(items.get(position));


        int pos = holder.getAdapterPosition();
        InOutBoxQuestionnaireItem item = items.get(pos);
        String questionid = item.id;
        holder.itemView.setOnClickListener(view -> RetrofitHelper.getInstance().showQuestionnaire(id, token, questionid, new Callback<InOutBoxQuestionnaireItem>() {
            @Override
            public void onResponse(Call<InOutBoxQuestionnaireItem> call, Response<InOutBoxQuestionnaireItem> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Intent intent = new Intent(view.getContext(), MainShowQuestionnaire.class);
                    intent.putExtra("category", response.body().category);
                    intent.putExtra("createAt", response.body().createdAt);
                    intent.putExtra("questions", response.body().questions.toString());
                    intent.putExtra("answer", response.body().answers.toString());
                    mContext.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<InOutBoxQuestionnaireItem> call, Throwable t) {
                Log.v("@@@@@@@", t.getMessage());
            }


        }));
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
        public JsonObject answers;
        public JsonObject questions;
        public boolean isBookmarked;
//        public HashMap<String, String> answers;
//        public HashMap<String, String> questions;

        public static InOutBoxQuestionnaireItem createFromInboxDto(InboxQuestionnaireDto dto) {
            InOutBoxQuestionnaireItem item = new InOutBoxQuestionnaireItem();
            item.category = dto.category;
            item.createdAt = dto.createdAt;
            item.id = dto.id;
            item.questionsCount = dto.questionsCount;
            item.senderId = dto.senderId;
            item.senderName = dto.senderName;
            item.answers = dto.answers;
            item.questions = dto.questions;
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
            item.answers = dto.answers;
            item.questions =dto.questions;
            return item;
        }
    }

    public class MainMyListDataViewHolder extends RecyclerView.ViewHolder {
        private MainMyListPagerAdapter.MyListType type;
        private TextView tvTitle;
        private TextView tvCategory;
        private TextView tvDate;

        public MainMyListDataViewHolder(@NonNull View itemView, MainMyListPagerAdapter.MyListType type) {
            super(itemView);
            this.type = type;

            tvTitle = itemView.findViewById(R.id.tv_mylist_title);
            tvCategory = itemView.findViewById(R.id.tv_mylist_category);
            tvDate = itemView.findViewById(R.id.tv_mylist_datetime);


//            itemView.setOnClickListener(view -> {
//
//                int pos = getAdapterPosition();
//                if (pos != RecyclerView.NO_POSITION) {
//                    InOutBoxQuestionnaireItem item = items.get(pos);
//
//                    RetrofitHelper.getInstance().showQuestionnaire(id, token, item.id, new Callback<InOutBoxQuestionnaireItem>() {
//                        @Override
//                        public void onResponse(Call<InOutBoxQuestionnaireItem> call, Response<InOutBoxQuestionnaireItem> response) {
//                            Toast.makeText(view.getContext(),"adsfasdf",Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<InOutBoxQuestionnaireItem> call, Throwable t) {
//                            Log.v("@@@@@@@@@@@@@@", t.getMessage());
//
//                        }
//                    });
//                }
//            });

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

            String contentText = itemView.getContext().getString(
                    type == MainMyListPagerAdapter.MyListType.SENT ? R.string.mylist_sent_to_s : R.string.mylist_received_from_s);

            tvTitle.setText(String.format(contentText, friendName));
            tvCategory.setText(item.category);
            tvDate.setText(item.createdAt);
        }
    }

}
