package com.mashup.nnaa.main.addfriends;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.model.FriendDto;

import java.util.ArrayList;

public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.ViewHolder> {

    private ArrayList<FriendDto> friendList;
    private Context fContext;

    public AddFriendAdapter(Context context, ArrayList<FriendDto> friendList) {
        this.fContext = context;
        this.friendList = friendList;
    }

    public void setFirendList(ArrayList<FriendDto> friendList) {
        this.friendList = friendList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddFriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addfriend_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFriendAdapter.ViewHolder holder, int position) {
        holder.txtCategory.setText(friendList.get(position).getType());
        holder.txtName.setText(friendList.get(position).getName());
        holder.txtEmail.setText(friendList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        if (friendList != null) {
            return friendList.size();
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
