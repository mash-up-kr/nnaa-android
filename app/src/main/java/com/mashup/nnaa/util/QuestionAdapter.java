package com.mashup.nnaa.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.QuestionItem;
import com.mashup.nnaa.network.model.Question;

import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    List<QuestionItem> questionList;
    Context Qcontext;

    public QuestionAdapter(Context context, ArrayList<QuestionItem> questionList) {
        this.Qcontext = context;
        this.questionList = questionList;
    }

    public void setQuestionList(ArrayList<QuestionItem> questionList) {
        this.questionList = questionList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = from(parent.getContext()).inflate(R.layout.question_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {

        holder.mName.setText(questionList.get(position).getQuestion_content());
        holder.mQ.setText(questionList.get(position).getQeustion_num());
    }


    @Override
    public int getItemCount() {
        if (questionList != null) {
            return questionList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mQ, mName;

        ViewHolder(View itemView) {
            super(itemView);

             mQ = itemView.findViewById(R.id.q_text);
            mName = itemView.findViewById(R.id.info_text);
        }
    }
}