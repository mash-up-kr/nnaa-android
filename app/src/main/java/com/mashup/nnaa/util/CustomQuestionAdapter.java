package com.mashup.nnaa.util;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.CustomQuestionItem;
import com.mashup.nnaa.data.QuestionItem;
import com.mashup.nnaa.question.QuestionActivity;

import java.util.ArrayList;

public class CustomQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int FIRST_CONTENT = 0;
    public static final int SECOND_CONTENT = 1;
    public static final int THIRD_CONTENT = 2;
    public static final int FORTH_CONTENT = 3;

    Context cContext;
    private ArrayList<QuestionItem> customQuestionItems;

    public CustomQuestionAdapter(Context context, ArrayList<QuestionItem> list) {
        this.customQuestionItems = list;
        this.cContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == FIRST_CONTENT) {
            assert inflater != null;
            view = inflater.inflate(R.layout.custom_first, parent, false);
            return new FirstCustomHolder(view);
        } else if (viewType == SECOND_CONTENT) {
            assert inflater != null;
            view = inflater.inflate(R.layout.custom_second, parent, false);
            return new SecondCustomHolder(view);
        } else if (viewType == THIRD_CONTENT) {
            assert inflater != null;
            view = inflater.inflate(R.layout.custom_third, parent, false);
            return new ThirdCustomHolder(view);
        } else {
            assert inflater != null;
            view = inflater.inflate(R.layout.custom_forth, parent, false);
            return new ForthCustomHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FirstCustomHolder) {
            ((FirstCustomHolder) holder).first_txt.setText(customQuestionItems.get(position).getQeustion_num());
            ((FirstCustomHolder) holder).first_edit.setText(customQuestionItems.get(position).getQuestion_content());

        } else if (holder instanceof SecondCustomHolder) {
            ((SecondCustomHolder) holder).second_txt.setText(customQuestionItems.get(position).getQeustion_num());
            ((SecondCustomHolder) holder).btn_j.setId(customQuestionItems.get(position).getQuestion_img());
            ((SecondCustomHolder) holder).btn_g.setId(customQuestionItems.get(position).getQuestion_img());
            ((SecondCustomHolder) holder).btn_o.setId(customQuestionItems.get(position).getQuestion_img());
        } else if (holder instanceof ThirdCustomHolder) {
            ((ThirdCustomHolder) holder).third_txt.setText(customQuestionItems.get(position).getQeustion_num());
            ((ThirdCustomHolder) holder).edit1.setText(customQuestionItems.get(position).getQuestion_content());
            ((ThirdCustomHolder) holder).edit2.setText(customQuestionItems.get(position).getQuestion_content());
            ((ThirdCustomHolder) holder).edit3.setText(customQuestionItems.get(position).getQuestion_content());
            ((ThirdCustomHolder) holder).edit4.setText(customQuestionItems.get(position).getQuestion_content());
            ((ThirdCustomHolder) holder).plus_btn1.setId(customQuestionItems.get(position).getQuestion_img());
            ((ThirdCustomHolder) holder).plus_btn2.setId(customQuestionItems.get(position).getQuestion_img());
            ((ThirdCustomHolder) holder).plus_btn3.setId(customQuestionItems.get(position).getQuestion_img());
            ((ThirdCustomHolder) holder).plus_btn4.setId(customQuestionItems.get(position).getQuestion_img());
            ((ThirdCustomHolder) holder).minus_btn1.setId(customQuestionItems.get(position).getQuestion_img());
            ((ThirdCustomHolder) holder).minus_btn2.setId(customQuestionItems.get(position).getQuestion_img());
            ((ThirdCustomHolder) holder).minus_btn3.setId(customQuestionItems.get(position).getQuestion_img());
            ((ThirdCustomHolder) holder).minus_btn4.setId(customQuestionItems.get(position).getQuestion_img());
        } else {
            ((ForthCustomHolder) holder).forth_txt.setText(customQuestionItems.get(position).getQeustion_num());
            ((ForthCustomHolder) holder).forth_img.setId(customQuestionItems.get(position).getQuestion_img());
        }
    }

    @Override
    public int getItemCount() {
        if (customQuestionItems != null) {
            return customQuestionItems.size();
        } else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return customQuestionItems.get(position).getViewType();
    }

    public static class FirstCustomHolder extends RecyclerView.ViewHolder {

        TextView first_txt;
        EditText first_edit;

        FirstCustomHolder(@NonNull View itemView) {
            super(itemView);

            first_txt = itemView.findViewById(R.id.first_txt);
            first_edit = itemView.findViewById(R.id.first_edit_custom);

        }
    }

    public static class SecondCustomHolder extends RecyclerView.ViewHolder {

        TextView second_txt;
        ImageButton btn_j, btn_g, btn_o;


        SecondCustomHolder(@NonNull View itemView) {
            super(itemView);

            second_txt = itemView.findViewById(R.id.second_txt);
            btn_j = itemView.findViewById(R.id.btn_j);
            btn_g = itemView.findViewById(R.id.btn_g);
            btn_o = itemView.findViewById(R.id.btn_o);
        }
    }

    private static class ThirdCustomHolder extends RecyclerView.ViewHolder {

        TextView third_txt;
        EditText edit1, edit2, edit3, edit4;
        ImageButton plus_btn1, plus_btn2, plus_btn3, plus_btn4, minus_btn1, minus_btn2, minus_btn3, minus_btn4;

        ThirdCustomHolder(View itemView) {
            super(itemView);

            third_txt = itemView.findViewById(R.id.third_txt);
            edit1 = itemView.findViewById(R.id.edit1);
            edit2 = itemView.findViewById(R.id.edit2);
            edit3 = itemView.findViewById(R.id.edit3);
            edit4 = itemView.findViewById(R.id.edit4);
            plus_btn1 = itemView.findViewById(R.id.plus_btn1);
            plus_btn2 = itemView.findViewById(R.id.plus_btn2);
            plus_btn3 = itemView.findViewById(R.id.plus_btn3);
            plus_btn4 = itemView.findViewById(R.id.plus_btn4);
            minus_btn1 = itemView.findViewById(R.id.minus_btn1);
            minus_btn2 = itemView.findViewById(R.id.minus_btn2);
            minus_btn3 = itemView.findViewById(R.id.minus_btn3);
            minus_btn4 = itemView.findViewById(R.id.minus_btn4);

            plus_btn1.setOnClickListener(view -> {
                plus_btn2.setVisibility(View.VISIBLE);
                edit2.setVisibility(View.VISIBLE);
                plus_btn1.setVisibility(View.GONE);
                minus_btn1.setVisibility(View.VISIBLE);
            });

            minus_btn1.setOnClickListener(view -> {
                // plus_btn1.setVisibility(View.GONE);
                edit1.setVisibility(View.GONE);
                minus_btn1.setVisibility(View.GONE);
            });

            plus_btn2.setOnClickListener(view -> {
                plus_btn3.setVisibility(View.VISIBLE);
                edit3.setVisibility(View.VISIBLE);
                plus_btn2.setVisibility(View.GONE);
                minus_btn2.setVisibility(View.VISIBLE);
            });

            minus_btn2.setOnClickListener(view -> {
                edit2.setVisibility(View.GONE);
                minus_btn2.setVisibility(View.GONE);
            });
            plus_btn3.setOnClickListener(view -> {
                minus_btn4.setVisibility(View.VISIBLE);
                edit4.setVisibility(View.VISIBLE);
                plus_btn3.setVisibility(View.GONE);
                minus_btn3.setVisibility(View.VISIBLE);
            });
            minus_btn3.setOnClickListener(view -> {
                edit3.setVisibility(View.GONE);
                minus_btn3.setVisibility(View.GONE);
            });
            minus_btn4.setOnClickListener(view -> {
                plus_btn4.setVisibility(View.GONE);
                edit4.setVisibility(View.GONE);
                plus_btn4.setVisibility(View.GONE);
                minus_btn4.setVisibility(View.GONE);
            });
        }
    }

    private static class ForthCustomHolder extends RecyclerView.ViewHolder {

        TextView forth_txt;
        ImageView forth_img;

        ForthCustomHolder(View itemView) {
            super(itemView);

            forth_txt = itemView.findViewById(R.id.forth_txt);
            forth_img = itemView.findViewById(R.id.forth_img);
        }
    }
}
