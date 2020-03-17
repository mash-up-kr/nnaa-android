package com.mashup.nnaa.main.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.MainHomeQuestionnairesItem;

import java.util.ArrayList;

public class MainQuestionnaireAdapter extends RecyclerView.Adapter<MainQuestionnairesViewHolder> {
    private ArrayList<MainHomeQuestionnairesItem> items = new ArrayList<>();

    public MainQuestionnaireAdapter() {
        // set an item that indicate 'empty'
        items.add(new MainHomeQuestionnairesItem());
        items.add(new MainHomeQuestionnairesItem());
        items.add(new MainHomeQuestionnairesItem());

    }

    @NonNull
    @Override
    public MainQuestionnairesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainQuestionnairesViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_main_home_questionaires, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainQuestionnairesViewHolder holder, int position) {
        holder.bind(position + 1, getItemCount(), items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(ArrayList<MainHomeQuestionnairesItem> data){
        if (data == null) {
            data = new ArrayList<>();
        }

        if (data.isEmpty()) {
            data.add(new MainHomeQuestionnairesItem());
        }

        items.clear();
        items.addAll(data);
        notifyDataSetChanged();
    }
}
