package com.mashup.nnaa.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.ReplySubjectItem;

import java.util.ArrayList;

public class ReplySubjectAdapter extends RecyclerView.Adapter<ReplySubjectAdapter.ViewHolder> {

    private ArrayList<ReplySubjectItem> subjectItems = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_subject_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplySubjectAdapter.ViewHolder holder, int position) {
        holder.onBind(subjectItems.get(position));
    }

    @Override
    public int getItemCount() {
        return subjectItems.size();
    }

    public void addItem(ReplySubjectItem replySubjectItem) {
        subjectItems.add(replySubjectItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private EditText replySubject;

        ViewHolder(View itemView) {
            super(itemView);

            replySubject = itemView.findViewById(R.id.subject_edit);
        }
        void onBind(ReplySubjectItem replySubjectItem) {
            replySubject.setText(replySubjectItem.getSubject_edit());
        }
    }
}
