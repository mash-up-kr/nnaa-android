package com.mashup.nnaa.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.mashup.nnaa.network.model.Questionnaire;

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

public class SharingAdapter extends RecyclerView.Adapter<SharingAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "SharingAdapter";

    private Context sContext;
    private ArrayList<Questionnaire> unFilterdList;
    private ArrayList<Questionnaire> filteredList;
    private String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
    private String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();
    private long now = System.currentTimeMillis();
    private Date date = new Date(now);
    private SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.KOREA);
    private String time = simple.format(date);

    private String KEY = "";

    public SharingAdapter(Context context, ArrayList<Questionnaire> list) {
        this.unFilterdList = list;
        this.filteredList = list;
        this.sContext = context;
    }

    public void setSharinglist(ArrayList<Questionnaire> list) {
        this.filteredList = list;
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {

                } else {
                    ArrayList<Questionnaire> filteringList = new ArrayList<>();
                    for (Questionnaire name : filteredList) {
                        if (name.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(name);
                        }
                    }
                    for (Questionnaire email : filteredList) {
                        if (email.getEmail().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(email);
                        }
                    }
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<Questionnaire>) filterResults.values;
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

        Bundle bundle = ((Activity) sContext).getIntent().getExtras();
        final String category = Objects.requireNonNull(bundle).getString("category");
        final String list = bundle.getString("list");

        int pos = holder.getAdapterPosition();

        Questionnaire item = filteredList.get(pos);

        AtomicReference<String> a = new AtomicReference<>();
        AtomicReference<String> b = new AtomicReference<>();
        AtomicReference<String> c = new AtomicReference<>();
        AtomicReference<String> d = new AtomicReference<>();
        AtomicReference<String> content = new AtomicReference<>();
        AtomicReference<String> type = new AtomicReference<>();
        AtomicReference<String> choice = new AtomicReference<>();

        JsonObject inputdata = new JsonObject();


        holder.userSelect.setOnClickListener(view -> {
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
                        }
                    }

                    @Override
                    public void onFailure(Call<Questionnaire> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public int getItemCount() {
        if (filteredList != null) {
            return filteredList.size();
        } else return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name, txt_email;
        Button userSelect;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.sharing_txt_name);
            txt_email = itemView.findViewById(R.id.sharing_txt_email);
            userSelect = itemView.findViewById(R.id.user_select);
        }
    }
}
