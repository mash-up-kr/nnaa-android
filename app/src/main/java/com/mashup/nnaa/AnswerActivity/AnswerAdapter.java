package com.mashup.nnaa.AnswerActivity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter{
    private ArrayList<AnswerItem> itemList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.layout_answer_item, parent, false);
        AnswerViewHolder answerVH = new AnswerViewHolder(view, new MyCustomEditTextListener());
        return answerVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AnswerItem item = itemList.get(position);

        TextView tvQuest = holder.itemView.findViewById(R.id.quest);
        EditText et = holder.itemView.findViewById(R.id.editquest);
        ((AnswerViewHolder)holder).getEditTextListener().updatePosition(position);

        tvQuest.setText(item.getQuest());
        et.setText(item.getAnswer());
    }

    public void addItem(AnswerItem item ) {
        itemList.add(item);
         notifyItemInserted(itemList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            itemList.get(position).setAnswer(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
