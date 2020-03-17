package com.mashup.nnaa.util;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mashup.nnaa.R;
import com.mashup.nnaa.data.DeleteQuestionItem;

import java.util.ArrayList;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.DeleteViewHolder> {


    private ArrayList<DeleteQuestionItem> deleteData = new ArrayList<>();

    @NonNull
    @Override
    public DeleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_question_item, parent, false);

        return new DeleteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DeleteViewHolder holder, int position) {

        holder.onBind(deleteData.get(position));
    }

    @Override
    public int getItemCount() {

        return deleteData.size();
    }

    public void addItem(DeleteQuestionItem deleteitem) {
        deleteData.add(deleteitem);
    }

    static class DeleteViewHolder extends RecyclerView.ViewHolder {

        private CheckBox check_box;
        private TextView txt_check;

        DeleteViewHolder(View itemView) {
            super(itemView);

            check_box = itemView.findViewById(R.id.check_box);

            check_box.setButtonDrawable(R.drawable.check_box);

            txt_check = itemView.findViewById(R.id.txt_check);


        }

        void onBind(DeleteQuestionItem deleteitem) {
            check_box.setText(deleteitem.getCheckBox());
            txt_check.setText(deleteitem.getQuestion_Txt());


        }
    }
}