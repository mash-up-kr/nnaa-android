package com.mashup.nnaa.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.model.bookmarkQuestionDto;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    private ArrayList<bookmarkQuestionDto> fList;
    private Context fContext;
    private String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
    private String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();

    public BookmarkAdapter(Context context, ArrayList<bookmarkQuestionDto> list) {
        this.fList = list;
        this.fContext = context;
    }

    public void setFavoriteList(ArrayList<bookmarkQuestionDto> fList) {
        this.fList = fList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_setting_favorite, parent, false);

        return new BookmarkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.ViewHolder holder, int position) {
        holder.txt_favorites_question.setText(fList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if (fList != null) {
            return fList.size();
        }
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_favorites_question;

        ViewHolder(View itemView) {
            super(itemView);

            txt_favorites_question = itemView.findViewById(R.id.question_text_msf);
        }
    }
}

