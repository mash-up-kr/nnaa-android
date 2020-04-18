package com.mashup.nnaa.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.question.LocalQuestionActivity;

import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

public class LocalQuestionAdapter extends RecyclerView.Adapter<LocalQuestionAdapter.ViewHolder> {

    private List<NewQuestionDto> list;
    private Context mContext;


    public LocalQuestionAdapter(Context context, List<NewQuestionDto> list) {
        this.mContext = context;
        this.list = list;
    }

    public void setLocalQuestionList(List<NewQuestionDto> questionList) {
        this.list = questionList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public LocalQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = from(parent.getContext()).inflate(R.layout.local_question_item, parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalQuestionAdapter.ViewHolder holder, int position) {
            holder.local_content.setText(list.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        if(list!=null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView local_content;

        ViewHolder(View itemView) {
            super(itemView);

            local_content = itemView.findViewById(R.id.local_question);

        }
    }
}
