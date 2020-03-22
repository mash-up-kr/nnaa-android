package com.mashup.nnaa.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.ReplyMultiItem;

import java.util.ArrayList;

public class ReplyMultiAdapter extends RecyclerView.Adapter<ReplyMultiAdapter.ViewHolder> {

    private ArrayList<ReplyMultiItem> replyMultiItems = new ArrayList<>();

    @NonNull
    @Override
    public ReplyMultiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_reply_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyMultiAdapter.ViewHolder holder, int position) {
        holder.onBind(replyMultiItems.get(position));
    }

    @Override
    public int getItemCount() {
        return replyMultiItems.size();
    }
    public void addMultiItem(ReplyMultiItem replyMultiItem) { replyMultiItems.add(replyMultiItem);}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView multi1,multi2,multi3,multi4;
        private Button  mulBtn1,mulBtn2,mulBtn3,mulBtn4;
        private ImageView mulImg1,mulImg2,mulImg3,mulImg4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            multi1 = itemView.findViewById(R.id.mutli_txt1);
            multi2 = itemView.findViewById(R.id.mutli_txt2);
            multi3 = itemView.findViewById(R.id.mutli_txt3);
            multi4 = itemView.findViewById(R.id.mutli_txt4);
            mulBtn1 = itemView.findViewById(R.id.multi_btn_a);
            mulBtn2 = itemView.findViewById(R.id.multi_btn_b);
            mulBtn3 = itemView.findViewById(R.id.multi_btn_c);
            mulBtn4 = itemView.findViewById(R.id.multi_btn_d);
            mulImg1 = itemView.findViewById(R.id.multi_img1);
            mulImg2 = itemView.findViewById(R.id.multi_img2);
            mulImg3 = itemView.findViewById(R.id.multi_img3);
            mulImg4 = itemView.findViewById(R.id.multi_img4);
        }
        void onBind(ReplyMultiItem replyMultiItem) {
            multi1.setText(replyMultiItem.getMulti_txt());
            multi2.setText(replyMultiItem.getMulti_txt());
            multi3.setText(replyMultiItem.getMulti_txt());
            multi4.setText(replyMultiItem.getMulti_txt());
            mulBtn1.setId(replyMultiItem.getMulti_btn());
            mulBtn2.setId(replyMultiItem.getMulti_btn());
            mulBtn3.setId(replyMultiItem.getMulti_btn());
            mulBtn4.setId(replyMultiItem.getMulti_btn());
            mulImg1.setId(replyMultiItem.getMulti_img());
            mulImg2.setId(replyMultiItem.getMulti_img());
            mulImg3.setId(replyMultiItem.getMulti_img());
            mulImg4.setId(replyMultiItem.getMulti_img());
        }
    }
}
