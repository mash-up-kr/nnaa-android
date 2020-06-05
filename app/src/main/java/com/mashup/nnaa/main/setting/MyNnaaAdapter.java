package com.mashup.nnaa.main.setting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.model.FriendDto;
import com.mashup.nnaa.select.SetTypeOfFriendActivity;

import java.util.ArrayList;

public class MyNnaaAdapter extends RecyclerView.Adapter<MyNnaaAdapter.ViewHolder> {

    private ArrayList<FriendDto> list;
    private Context mContext;

    public MyNnaaAdapter(Context context, ArrayList<FriendDto> sendList) {
        this.mContext = context;
        this.list = sendList;
    }

    public void setSendList(ArrayList<FriendDto> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyNnaaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nofriend_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNnaaAdapter.ViewHolder holder, int position) {
        holder.txtCategory.setText(list.get(position).getCategory());
        holder.txtName.setText(list.get(position).getName());
        holder.txtEmail.setText(list.get(position).getEmail());

        int pos = holder.getAdapterPosition();

        FriendDto item = list.get(pos);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SetTypeOfFriendActivity.class);
            intent.putExtra("friend_name",item.getName());
            mContext.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCategory, txtName, txtEmail;

        ViewHolder(View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txt_category);
            txtName = itemView.findViewById(R.id.addfriend_username);
            txtEmail = itemView.findViewById(R.id.addfriend_email);
        }
    }
}
