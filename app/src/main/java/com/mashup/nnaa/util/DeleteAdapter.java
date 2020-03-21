package com.mashup.nnaa.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mashup.nnaa.R;
import com.mashup.nnaa.data.DeleteQuestionItem;
import com.mashup.nnaa.data.QuestionItem;

import java.util.ArrayList;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.DeleteViewHolder> implements ItemTouchHelperListener {


    private ArrayList<QuestionItem> deleteList;
    private Context dContext;

    public DeleteAdapter(Context context, ArrayList<QuestionItem> list) {
        this.deleteList = list;
        this.dContext = context;
    }

    @NonNull
    @Override
    public DeleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_question_item, parent, false);

        return new DeleteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DeleteViewHolder holder, int position) {
        holder.delete_num.setText(deleteList.get(position).getQeustion_num());
        holder.delete_content.setText(deleteList.get(position).getQuestion_content());
    }

    @Override
    public int getItemCount() {

        if (deleteList != null) {
            return deleteList.size();
        } else return 0;
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        QuestionItem questionItem = deleteList.get(from_position);
        deleteList.remove(from_position);
        deleteList.add(to_position, questionItem);

        notifyItemMoved(from_position, to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        deleteList.remove(position);
        notifyItemRemoved(position);

    }

    public class DeleteViewHolder extends RecyclerView.ViewHolder {

        private TextView delete_num, delete_content;

        public DeleteViewHolder(View itemView) {
            super(itemView);
            this.delete_num = itemView.findViewById(R.id.delete_num);
            this.delete_content = itemView.findViewById(R.id.delete_content);
        }
    }
}