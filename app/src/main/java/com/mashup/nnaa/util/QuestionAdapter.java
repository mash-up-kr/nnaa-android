package com.mashup.nnaa.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.LayoutInflater.from;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private ArrayList<NewQuestionDto> questionList;
    private Context Qcontext;
    private String TAG = "QuestionAdatper";
    private String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
    private String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();

    public QuestionAdapter(Context context, ArrayList<NewQuestionDto> questionList) {
        this.Qcontext = context;
        this.questionList = questionList;
    }

    public void setQuestionList(ArrayList<NewQuestionDto> questionList) {
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
        holder.qChoice.setChecked(questionList.get(position).isBookmarked());

        Bundle bundle = ((Activity) Qcontext).getIntent().getExtras();

        final String category = Objects.requireNonNull(bundle).getString("category");

        holder.qChoice.setOnCheckedChangeListener(null);

        holder.qChoice.setOnCheckedChangeListener((buttonView, isChekced) -> {

            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                NewQuestionDto item = questionList.get(pos);

                NewQuestionDto newQuestionDto = new NewQuestionDto(item.getContent(), category, item.getType(), item.getChoices(), item.isBookmarked());
                if (holder.qChoice.isChecked()) {
                    RetrofitHelper.getInstance().favoriteEnroll(id, token, newQuestionDto, new Callback<NewQuestionDto>() {
                        @Override
                        public void onResponse(Call<NewQuestionDto> call, Response<NewQuestionDto> response) {
                            newQuestionDto.setBookmarked(true);

                            holder.qChoice.setChecked(newQuestionDto.isBookmarked());
                            holder.qChoice.setChecked(isChekced);
                            SharedPrefHelper.getInstance().put("check", holder.qChoice.isChecked());

                        }

                        @Override
                        public void onFailure(Call<NewQuestionDto> call, Throwable t) {
                            Log.v(TAG, t.getMessage());
                        }
                    });
                } else {
                    // 즐겨찾기 해제
                    RetrofitHelper.getInstance().favoriteDelete(id, token, item.getId(), new Callback<NewQuestionDto>() {
                        @Override
                        public void onResponse(Call<NewQuestionDto> call, Response<NewQuestionDto> response) {
                            holder.qChoice.setChecked(false);

                            item.setBookmarked(false);
                            newQuestionDto.setBookmarked(false);
                            holder.qChoice.setChecked(false);
                        }

                        @Override
                        public void onFailure(Call<NewQuestionDto> call, Throwable t) {
                            Log.v(TAG, t.getMessage());
                        }
                    });
                }
            }
        });
    }

    public void addItem(NewQuestionDto newQuestionDto) {
        questionList.add(newQuestionDto);
        notifyDataSetChanged();
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

            SharedPrefHelper.getInstance().getBoolean("check", false);
        }
    }
}

