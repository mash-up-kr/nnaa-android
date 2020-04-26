package com.mashup.nnaa.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.model.SharingDto;

import java.util.ArrayList;

public class SharingAdapter extends RecyclerView.Adapter<SharingAdapter.ViewHolder> implements Filterable {

    Context sContext;
    ArrayList<SharingDto> unFilterdList;
    ArrayList<SharingDto> filteredList;

    public SharingAdapter(Context context, ArrayList<SharingDto> list) {
        this.unFilterdList = list;
        this.filteredList = list;
        this.sContext = context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()) {
                    filteredList = unFilterdList;
                } else {
                    ArrayList<SharingDto> filteringList = new ArrayList<>();
                    /*for(SharingDto name: unFilterdList) {
                        if(name.toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(name);
                        }
                    }
                    for(SharingDto email: unFilterdList) {
                        if(email.toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(email);
                        }
                    }*/
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<SharingDto>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(sContext).inflate(R.layout.activity_sharing_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txt_name.setText(filteredList.get(position).getName());
            holder.txt_email.setText(filteredList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        if (filteredList != null) {
            return filteredList.size();
        } else return 0;
    }

    public void addItem(SharingDto sharingDto) {
        filteredList.add(sharingDto);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name, txt_email;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.sharing_txt_name);
            txt_email = itemView.findViewById(R.id.sharing_txt_email);
        }
    }
}
