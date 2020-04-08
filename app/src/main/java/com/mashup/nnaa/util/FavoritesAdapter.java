package com.mashup.nnaa.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.QuestionItem;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.network.model.Question;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<NewQuestionDto> fList;
    private Context fContext;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private String id;
    private String token;
    private String questionId;

    public FavoritesAdapter(Context context, List<NewQuestionDto> list) {
        this.fList = list;
        this.fContext = context;
    }

    public void setFavoritList(List<NewQuestionDto> fList) {
        this.fList = fList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_question_item, parent, false);

        ViewHolder vh = new ViewHolder(view);


        // check box 리스너
        preferences = vh.check_box_favorites.getContext().getSharedPreferences("check", Context.MODE_PRIVATE);
        vh.check_box_favorites.setOnCheckedChangeListener(null);
        vh.check_box_favorites.setChecked(vh.check_box_favorites.isSelected());

        vh.check_box_favorites.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(view.getContext(), "즐겨찾기", Toast.LENGTH_LONG).show();
                preferences = buttonView.getContext().getSharedPreferences("check", MODE_PRIVATE);
                editor = preferences.edit();
                editor.putBoolean("check", vh.check_box_favorites.isChecked());
                editor.apply();

                RetrofitHelper.getInstance().favoriteEnroll(id, token, questionId, new Callback<NewQuestionDto>() {
                    @Override
                    public void onResponse(Call<NewQuestionDto> call, Response<NewQuestionDto> response) {
                        Log.v("즐겨찾기 등록", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Call<NewQuestionDto> call, Throwable t) {
                        Log.v("즐겨찾기 등록", "실패:"+ t.getMessage());
                    }
                });
                Log.v("즐겨찾기", "Click!");
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {
        holder.onBind(fList.get(position));

    }

    @Override
    public int getItemCount() {
        if (fList != null) {
            return fList.size();
        } else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_favorites_question;
        private CheckBox check_box_favorites;

        ViewHolder(View itemView) {
            super(itemView);

            txt_favorites_question = itemView.findViewById(R.id.txt_favorites_question);
            check_box_favorites = itemView.findViewById(R.id.check_box_favorites);

        }

        void onBind(NewQuestionDto questionItem) {
            txt_favorites_question.setText(questionItem.getContent());
            //check_box_favorites.setChecked(Boolean.parseBoolean(questionItem.getId()));

        }
    }
}
