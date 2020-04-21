package com.mashup.nnaa.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.mashup.nnaa.data.QuestionItem;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.network.model.Question;
import com.mashup.nnaa.network.model.bookmarkQuestionDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private ArrayList<NewQuestionDto> fList;
    private Context fContext;
    private String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
    private String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();

    public FavoritesAdapter(Context context, ArrayList<NewQuestionDto> list) {
        this.fList = list;
        this.fContext = context;
    }

    public void setFavoritList(ArrayList<NewQuestionDto> fList) {
        this.fList = fList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_question_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {

        holder.txt_favorites_question.setText(fList.get(position).getContent());

        holder.check_box_favorites.setOnClickListener(view -> {

            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                NewQuestionDto item = fList.get(pos);

                RetrofitHelper.getInstance().favoriteDelete(id, token, item.getId(), new Callback<NewQuestionDto>() {
                    @Override
                    public void onResponse(Call<NewQuestionDto> call, Response<NewQuestionDto> response) {
                        deleteFavorites(pos);
                        Log.v("delete bookmark", "position" + pos);
                    }

                    @Override
                    public void onFailure(Call<NewQuestionDto> call, Throwable t) {

                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        if (fList != null) {
            return fList.size();
        } else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_favorites_question;
        private ImageView check_box_favorites;

        ViewHolder(View itemView) {
            super(itemView);

            txt_favorites_question = itemView.findViewById(R.id.txt_favorites_question);
            check_box_favorites = itemView.findViewById(R.id.check_box_favorites);

        }

    }

    private void deleteFavorites(int position) {
        fList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, fList.size());
    }
}
