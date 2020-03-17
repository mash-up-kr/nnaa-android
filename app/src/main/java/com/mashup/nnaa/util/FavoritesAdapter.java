package com.mashup.nnaa.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.FavoritesItem;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private ArrayList<FavoritesItem> fDataset = new ArrayList<>();
    public Context mContext = null;
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;

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
            if(isChecked) {
                Toast.makeText(view.getContext(), "즐겨찾기",Toast.LENGTH_LONG).show();
                preferences = buttonView.getContext().getSharedPreferences("check",MODE_PRIVATE);
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
        holder.onBind(fDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return fDataset.size();
    }

    public void addItem(FavoritesItem fitem) {
        fDataset.add(fitem);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_favorites_question;
        private CheckBox check_box_favorites;

        ViewHolder(View itemView) {
            super(itemView);

            txt_favorites_question = itemView.findViewById(R.id.txt_favorites_question);
            check_box_favorites = itemView.findViewById(R.id.check_box_favorites);
        }

        void onBind(FavoritesItem fitem) {
            txt_favorites_question.setText(fitem.getFavoritesQuestion());
            check_box_favorites.setChecked(fitem.isFavoirtesCheck());
        }
    }
}
