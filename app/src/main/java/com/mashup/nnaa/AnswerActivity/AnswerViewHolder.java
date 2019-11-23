package com.mashup.nnaa.AnswerActivity;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;

public class AnswerViewHolder extends RecyclerView.ViewHolder {
    AnswerAdapter.MyCustomEditTextListener editTextListener;
    EditText etAnswer;
    public AnswerViewHolder(@NonNull View itemView, AnswerAdapter.MyCustomEditTextListener editTextListener) {
        super(itemView);
        etAnswer = itemView.findViewById(R.id.editquest);
        this.editTextListener = editTextListener;
        etAnswer.addTextChangedListener(this.editTextListener);
    }

    public AnswerAdapter.MyCustomEditTextListener getEditTextListener() {
        return editTextListener;
    }
}
