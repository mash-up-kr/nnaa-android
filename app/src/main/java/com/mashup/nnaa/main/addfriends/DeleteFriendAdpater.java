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
import com.mashup.nnaa.util.ItemTouchHelperListener;

import java.util.ArrayList;

public class DeleteFriendAdpater extends RecyclerView.Adapter<DeleteFriendAdpater.ViewHolder> implements ItemTouchHelperListener {

    private ArrayList<FriendDto> deleteList;
    private Context dContext;

    public DeleteFriendAdpater(Context context, ArrayList<FriendDto> list) {
        this.dContext = context;
        this.deleteList = list;
    }

    public void setFirendList(ArrayList<FriendDto> list) {
        this.deleteList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeleteFriendAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addfriend_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteFriendAdpater.ViewHolder holder, int position) {
        holder.txtCategory.setText(deleteList.get(position).getType());
        holder.txtName.setText(deleteList.get(position).getName());
        holder.txtEmail.setText(deleteList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        if (deleteList != null) {
            return deleteList.size();
        }
        return 0;
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        FriendDto friendDto = deleteList.get(from_position);
        deleteList.remove(from_position);
        deleteList.add(to_position, friendDto);

        notifyItemMoved(from_position, to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        deleteList.remove(position);
        notifyItemRemoved(position);
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
