package com.mashup.nnaa.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.ContactItem;

import java.util.ArrayList;

public class SelectFriendAdapter extends RecyclerView.Adapter<SelectFriendViewHolder> {
    private ArrayList<ContactItem> items = new ArrayList<>();
    private IFriendItemClickListener clickListener;

    public SelectFriendAdapter(IFriendItemClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public SelectFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_friend_item, parent, false);
        return new SelectFriendViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectFriendViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(ArrayList<ContactItem> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }

    public interface IFriendItemClickListener {
        void onFriendItemClicked(ContactItem contactItem);
    }
}

class SelectFriendViewHolder extends RecyclerView.ViewHolder {

    private TextView tvName;
    private TextView tvNumber;
    private ContactItem currentItem;

    SelectFriendViewHolder(@NonNull View itemView, SelectFriendAdapter.IFriendItemClickListener listener) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_friend_name);
        tvNumber = itemView.findViewById(R.id.tv_friend_number);
        itemView.setOnClickListener(v -> listener.onFriendItemClicked(currentItem));
    }

    void bind(ContactItem item) {
        tvName.setText(item.getName());
        tvNumber.setText(item.getNumber());
        currentItem = item;
    }
}

