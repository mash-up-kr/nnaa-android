package com.mashup.nnaa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.data.FavoritesItem;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private ArrayList<FavoritesItem> fDataset = new ArrayList<>();

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_question_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {


        holder.onBind(fDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return fDataset.size();

    }

    void addItem(FavoritesItem fitem) {

        fDataset.add(fitem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_favorites_question;
        private CheckBox check_box_favorites;

        ViewHolder(View itemView) {
            super(itemView);


            txt_favorites_question = itemView.findViewById(R.id.txt_favorites_question);

            check_box_favorites = itemView.findViewById(R.id.check_box_favorites);

            check_box_favorites.setButtonDrawable(R.drawable.favorites_check_box);


        }

        void onBind(FavoritesItem fitem) {

            txt_favorites_question.setText(fitem.getFavoritesQuestion());
            check_box_favorites.setId((fitem.getFavoritesImg()));
        }
    }
}
