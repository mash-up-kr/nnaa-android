package com.mashup.nnaa.main.mylist;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.network.model.QuestionnaireDto;

import java.util.ArrayList;

public class MainMyListDataAdapter extends RecyclerView.Adapter {
    private ArrayList<QuestionnaireDto> items;

    public MainMyListDataAdapter() {
        items = new ArrayList<>();
    }

    public void setData(ArrayList<QuestionnaireDto> data) {
        this.items = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
