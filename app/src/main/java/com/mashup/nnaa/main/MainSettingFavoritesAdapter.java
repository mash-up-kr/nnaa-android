package com.mashup.nnaa.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.util.AccountManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainSettingFavoritesAdapter extends RecyclerView.Adapter<MainSettingFavoritesAdapter.ViewHolder> {

    private ArrayList<NewQuestionDto> fList;
    private Context fContext;
    private String TAG = "MainSettingFavoritesAdapter";
    private String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
    private String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();

    public MainSettingFavoritesAdapter(Context context, ArrayList<NewQuestionDto> list) {
        this.fList = list;
        this.fContext = context;
    }

    public void setFavoritList(ArrayList<NewQuestionDto> fList) {
        this.fList = fList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainSettingFavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_setting_favorite, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainSettingFavoritesAdapter.ViewHolder holder, int position) {

        holder.content_txt.setText(fList.get(position).getContent());

        holder.q_txt.setOnClickListener(view -> {

            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                NewQuestionDto item = fList.get(pos);

                RetrofitHelper.getInstance().favoriteDelete(id, token, item.getId(), new Callback<NewQuestionDto>() {
                    @Override
                    public void onResponse(Call<NewQuestionDto> call, Response<NewQuestionDto> response) {
                        deleteFavorites(pos);
                        Log.v(TAG, "position" + pos);
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

        private TextView q_txt, content_txt;

        ViewHolder(View itemView) {
            super(itemView);

            q_txt = itemView.findViewById(R.id.q_text_msf);
            content_txt = itemView.findViewById(R.id.question_text_msf);

        }

    }

    private void deleteFavorites(int position) {
        fList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, fList.size());
    }
}
