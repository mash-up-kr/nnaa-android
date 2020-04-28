package com.mashup.nnaa.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashup.nnaa.R;
import com.mashup.nnaa.data.Choices;
import com.mashup.nnaa.main.MainActivity;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.AdditionalProp;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.network.model.Questionnaire;
import com.mashup.nnaa.network.model.Questions;
import com.mashup.nnaa.network.model.SharingDto;
import com.mashup.nnaa.question.QuestionActivity;
import com.mashup.nnaa.question.SharingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SharingAdapter extends RecyclerView.Adapter<SharingAdapter.ViewHolder> implements Filterable {

    private Context sContext;
    private ArrayList<Questionnaire> unFilterdList;
    private ArrayList<Questionnaire> filteredList;
    private String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
    private String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();
    private long now = System.currentTimeMillis();
    private Date date = new Date(now);
    private SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.KOREA);
    private String time = simple.format(date);
    private JSONArray jsonArray;
    Choices choices = new Choices();
    AdditionalProp additionalProp = new AdditionalProp();
    Questions questions = new Questions();

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

        int pos = holder.getAdapterPosition();
        if (pos != RecyclerView.NO_POSITION) {
            Questionnaire item = filteredList.get(pos);

            holder.userSelect.setOnClickListener(view -> {

                Bundle bundle = ((Activity) sContext).getIntent().getExtras();
                final String category = bundle.getString("category");
                final String list = bundle.getString("list");
                Log.v("@@@@@@@@@@","테스트:"+list);
                try {
                    jsonArray = new JSONArray(list);
                    Log.v("@@@@", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (choices != null & additionalProp != null & questions != null) {
                            choices.setA(jsonObject.getString(choices.getA()));
                            choices.setB(jsonObject.getString(choices.getB()));
                            choices.setC(jsonObject.getString(choices.getC()));
                            choices.setD(jsonObject.getString(choices.getD()));
                            additionalProp.setChoices(choices);
                            additionalProp.setType(jsonObject.getString(additionalProp.getType()));
                            additionalProp.setContent(jsonObject.getString(additionalProp.getContent()));
                            questions.setAdditionalProp(additionalProp);
                            additionalProp.setChoices(choices);
                            questions.setAdditionalProp(additionalProp);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Questionnaire questionnaire = new Questionnaire(category, time, questions, item.getId());

                AlertDialog.Builder builder = new AlertDialog.Builder(sContext);
                builder.setTitle("해당 친구에게 보내기");
                builder.setMessage("질문지를 보낼까요?");
                builder.setPositiveButton("확인", (dialogInterface, i) -> RetrofitHelper.getInstance().postQuestionnaire(id, token, questionnaire, new Callback<Questionnaire>() {
                    @Override
                    public void onResponse(Call<Questionnaire> call, Response<Questionnaire> response) {
                        Toast.makeText(getApplicationContext(), "질문지를 보내겠습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        sContext.startActivity(intent);
                        Log.v("@@", "response:" + response.code());
                    }

                    @Override
                    public void onFailure(Call<Questionnaire> call, Throwable t) {

                    }
                }));
                builder.setNegativeButton("취소", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "취소하였습니다.", Toast.LENGTH_SHORT).show());
                builder.show();
            });
        }
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
