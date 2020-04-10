package com.mashup.nnaa.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.model.NewQuestionDto;

import java.util.List;

import static android.view.LayoutInflater.from;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    List<NewQuestionDto> questionDtoList;
    Context mContext;
    private ClickCallbackListener callbackListener;

    public void setCallbackListener(ClickCallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    public ReplyAdapter(Context context, List<NewQuestionDto> questionDtos) {
        this.questionDtoList = questionDtos;
        this.mContext = context;
    }

    public void setQuestionDtoList(List<NewQuestionDto> questionDtoList) {
        this.questionDtoList = questionDtoList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ReplyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = from(parent.getContext()).inflate(R.layout.reply_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyAdapter.ViewHolder holder, int position) {
        holder.answerQ.setText(questionDtoList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        if (questionDtoList != null) {
            return questionDtoList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView answerQ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            answerQ = itemView.findViewById(R.id.answer_question);

            setListener();
        }

        private void setListener() {
            answerQ.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            callbackListener.callBack(getAdapterPosition());
        }
    }
}
