package com.mashup.nnaa.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.QuestionItem;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {


    private ArrayList<QuestionItem> mDataset = new ArrayList<>();

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {

        holder.onBind(mDataset.get(position));

    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addItem(QuestionItem aitem) {

        mDataset.add(aitem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mQ, mName;

        ViewHolder(View itemView) {
            super(itemView);

            mQ = itemView.findViewById(R.id.q_text);
            mName = itemView.findViewById(R.id.info_text);
        }

        void onBind(QuestionItem aitem) {

            mQ.setText(aitem.getMainQ());
            mName.setText(aitem.getQuestionary());
        }
    }
}