package com.mashup.nnaa.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.network.model.Question;
import com.mashup.nnaa.network.model.Question;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.LayoutInflater.from;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    List<NewQuestionDto> questionList;
    Context Qcontext;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public QuestionAdapter(Context context, List<NewQuestionDto> questionList) {
        this.Qcontext = context;
        this.questionList = questionList;
    }

    public void setQuestionList(List<NewQuestionDto> questionList) {
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

        holder.mName.setText(questionList.get(position).getContent());

       /* holder.qChoice.setOnCheckedChangeListener(null);
        holder.qChoice.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (holder.qChoice.isChecked()) {
                Toast.makeText(buttonView.getContext(), "즐겨찾기 추가", Toast.LENGTH_LONG).show();
                preferences = buttonView.getContext().getSharedPreferences("check", Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putBoolean("check", holder.qChoice.isChecked());
                editor.apply();
                holder.qChoice.setChecked(true);
            }else {
                Toast.makeText(buttonView.getContext(),"즐겨찾기 해제",Toast.LENGTH_SHORT).show();
            }
        });*/
    }


    @Override
    public int getItemCount() {
        if (questionList != null) {
            return questionList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private CheckBox qChoice;

        ViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.info_text);
            qChoice = itemView.findViewById(R.id.question_choice);

          /*  Bundle bundle = ((Activity) Qcontext).getIntent().getExtras();

            final String id = bundle.getString("id");
            final String token = bundle.getString("token");*/
            final String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
            final String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();
            final String questionId = "5e8c13f10509a8373b106865";
            qChoice.setOnCheckedChangeListener(null);
            qChoice.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (qChoice.isChecked()) {
                    RetrofitHelper.getInstance().favoriteEnroll(id, token, questionId, new Callback<NewQuestionDto>() {
                        @Override
                        public void onResponse(Call<NewQuestionDto> call, Response<NewQuestionDto> response) {
                            qChoice.setChecked(true);
                            Log.v("@@@@@@@", "id : " + id + "token: " + token + "questionId: " + response.body().getId());
                            Toast.makeText(buttonView.getContext(), "즐겨찾기 추가", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<NewQuestionDto> call, Throwable t) {

                        }
                    });
                } else {
                    RetrofitHelper.getInstance().favoriteDelete(id, token, questionId, new Callback<NewQuestionDto>() {
                        @Override
                        public void onResponse(Call<NewQuestionDto> call, Response<NewQuestionDto> response) {
                            Toast.makeText(buttonView.getContext(), "즐겨찾기 해제", Toast.LENGTH_SHORT).show();
                            Log.v("#######", "id : " + id + "token: " + token + "questionId: " + response.body().getId());
                            response.body().getId();
                            qChoice.setChecked(false);
                        }

                        @Override
                        public void onFailure(Call<NewQuestionDto> call, Throwable t) {

                        }
                    });
                }

            });
        }
    }
}