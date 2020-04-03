package com.mashup.nnaa.util;

import android.content.Context;
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

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>{

    private ArrayList<QuestionItem> fList;
    private Context fContext;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public FavoritesAdapter(Context context, ArrayList<QuestionItem> list) {
        this.fList = list;
        this.fContext = context;
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

        void onBind(QuestionItem questionItem) {
            txt_favorites_question.setText(questionItem.getQuestion_content());
            check_box_favorites.setChecked(Boolean.parseBoolean(questionItem.getQeustion_num()));

        }
    }
}
