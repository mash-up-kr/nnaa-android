package com.mashup.nnaa.main.addfriends;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.mashup.nnaa.R;
import com.mashup.nnaa.main.MainActivity;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.FriendDto;
import com.mashup.nnaa.network.model.Questionnaire;
import com.mashup.nnaa.util.AccountManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SendFriendAdapter extends RecyclerView.Adapter<SendFriendAdapter.ViewHolder> implements Filterable {

    private ArrayList<FriendDto> unFilterdList;
    private ArrayList<FriendDto> filteredList;
    private Context sContext;

    private String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
    private String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();
    private long now = System.currentTimeMillis();
    private Date date = new Date(now);
    private SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.KOREA);
    private String time = simple.format(date);
    private String KEY = "";

    public SendFriendAdapter(Context context, ArrayList<FriendDto> sendList) {
        this.sContext = context;
        this.unFilterdList = sendList;
        this.filteredList = sendList;
    }

    public void setSendList(ArrayList<FriendDto> list) {
        this.filteredList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SendFriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sendfriend_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendFriendAdapter.ViewHolder holder, int position) {
        holder.send_name.setText(filteredList.get(position).getName());
        holder.send_email.setText(filteredList.get(position).getEmail());

        Bundle bundle = ((Activity) sContext).getIntent().getExtras();
        final String category = Objects.requireNonNull(bundle).getString("category");
        final String list = bundle.getString("list");

        int pos = holder.getAdapterPosition();

        FriendDto item = filteredList.get(pos);

        AtomicReference<String> a = new AtomicReference<>();
        AtomicReference<String> b = new AtomicReference<>();
        AtomicReference<String> c = new AtomicReference<>();
        AtomicReference<String> d = new AtomicReference<>();
        AtomicReference<String> content = new AtomicReference<>();
        AtomicReference<String> type = new AtomicReference<>();
        AtomicReference<String> choice = new AtomicReference<>();
        JsonObject inputdata = new JsonObject();

        holder.friendSelect.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("친구에게 질문 보내기").setMessage("질문지를 보낼까요?");
            builder.setPositiveButton("보내기", (dialog, which) -> {
                Toast.makeText(getApplicationContext(), "질문지를 보내겠습니다.", Toast.LENGTH_SHORT).show();
                try {
                    JSONArray array = new JSONArray(list);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        KEY = "additionalProp" + (i + 1);
                        content.set(object.getString("content"));
                        type.set(object.getString("type"));
                        choice.set(object.getString("choices"));

                        JSONObject c_object = new JSONObject(String.valueOf(Objects.requireNonNull(choice)));

                        a.set(c_object.optString("a", "보기가 없습니다."));
                        b.set(c_object.optString("b", "보기가 없습니다."));
                        c.set(c_object.optString("c", "보기가 없습니다."));
                        d.set(c_object.optString("d", "보기가 없습니다."));

                        JsonObject middledata = new JsonObject();
                        JsonObject choicedata = new JsonObject();

                        choicedata.addProperty("a", a.get());
                        choicedata.addProperty("b", b.get());
                        choicedata.addProperty("c", c.get());
                        choicedata.addProperty("d", d.get());

                        middledata.add("choices", choicedata);
                        middledata.addProperty("content", content.get());
                        middledata.addProperty("type", type.get());
                        Log.v("미들데이타", middledata.toString());

                        inputdata.add(KEY, middledata);
                    }

                    Questionnaire questionnaire = new Questionnaire(category, time, inputdata, item.getId());
                    RetrofitHelper.getInstance().postQuestionnaire(id, token, questionnaire, new Callback<Questionnaire>() {
                        @Override
                        public void onResponse(Call<Questionnaire> call, Response<Questionnaire> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "질문지를 보내겠습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                sContext.startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "친구 등록 오류", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Questionnaire> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "(질문지 보내기 오류)", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            builder.setNegativeButton("취소", (dialog, which) -> Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_SHORT).show());
            builder.show();

        });
    }

    @Override
    public int getItemCount() {
        if (filteredList != null) {
            return filteredList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredList = unFilterdList;
                } else {
                    ArrayList<FriendDto> filteringList = new ArrayList<>();
                    for (FriendDto friendDto : unFilterdList) {
                        if (friendDto.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(friendDto);
                        }
                    }
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<FriendDto>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView send_name, send_email;
        Button friendSelect;

        ViewHolder(View itemView) {
            super(itemView);

            send_name = itemView.findViewById(R.id.send_txt_name);
            send_email = itemView.findViewById(R.id.send_txt_email);
            friendSelect = itemView.findViewById(R.id.friend_select);
        }
    }
}
