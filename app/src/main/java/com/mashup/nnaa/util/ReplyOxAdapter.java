package com.mashup.nnaa.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.ReplyOxItem;

import java.util.ArrayList;

public class ReplyOxAdapter extends RecyclerView.Adapter<ReplyOxAdapter.ViewHolder> {

    private ArrayList<ReplyOxItem> oxItems = new ArrayList<>();

    @NonNull
    @Override
    public ReplyOxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_ox_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyOxAdapter.ViewHolder holder, int position) {
        holder.onBind(oxItems.get(position));
    }

    @Override
    public int getItemCount() {
        return oxItems.size();
    }

    public void addOxItem(ReplyOxItem replyOxItem) {
        oxItems.add(replyOxItem);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_o, img_x;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_o = itemView.findViewById(R.id.reply_o_btn);
            img_x = itemView.findViewById(R.id.reply_x_btn);

        }

        void onBind(ReplyOxItem replyOxItem) {
            img_o.setId(replyOxItem.getImage_o());
            img_x.setId(replyOxItem.getImag_x());

        }
    }
}
