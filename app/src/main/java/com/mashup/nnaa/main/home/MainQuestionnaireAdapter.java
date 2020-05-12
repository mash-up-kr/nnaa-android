package com.mashup.nnaa.main.home;

import android.content.Intent;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mashup.nnaa.R;
import com.mashup.nnaa.main.mylist.MainMyListDataAdapter;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.InboxQuestionnaireDto;
import com.mashup.nnaa.reply.ReplyActivity;
import com.mashup.nnaa.util.AccountManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainQuestionnaireAdapter extends RecyclerView.Adapter<MainQuestionnaireAdapter.MainQuestionnairesViewHolder> {
    private ArrayList<InboxQuestionnaireDto> items = new ArrayList<>();
    private MainHomeFragment mContext;
    private String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
    private String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();

    public MainQuestionnaireAdapter() {
        // set an item that indicate 'empty'
        items.add(new InboxQuestionnaireDto());
        items.add(new InboxQuestionnaireDto());
        items.add(new InboxQuestionnaireDto());

    }

    @NonNull
    @Override
    public MainQuestionnairesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainQuestionnairesViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_main_home_questionaires, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainQuestionnairesViewHolder holder, int position) {
        holder.bind(position + 1, getItemCount(), items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(ArrayList<InboxQuestionnaireDto> data, MainHomeFragment context) {
        if (data == null) {
            data = new ArrayList<>();
        }

        if (data.isEmpty()) {
            data.add(new InboxQuestionnaireDto());
        }
        this.mContext = context;

        items.clear();
        items.addAll(data);
        notifyDataSetChanged();
    }

    public class MainQuestionnairesViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIndex;
        private TextView tvSender;

        public MainQuestionnairesViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIndex = itemView.findViewById(R.id.tv_index);
            tvSender = itemView.findViewById(R.id.tv_sender);

            itemView.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                InboxQuestionnaireDto item = items.get(pos);
                String questionid = item.questionnairesId;
                RetrofitHelper.getInstance().showQuestionnaire(id, token, questionid, new Callback<MainMyListDataAdapter.InOutBoxQuestionnaireItem>() {
                    @Override
                    public void onResponse(Call<MainMyListDataAdapter.InOutBoxQuestionnaireItem> call, Response<MainMyListDataAdapter.InOutBoxQuestionnaireItem> response) {
                        Intent intent = new Intent(view.getContext(), ReplyActivity.class);
                        if (response.body() != null) {
                            intent.putExtra("questions", response.body().questions.toString());
                            intent.putExtra("id", response.body().id);
                        }
//                        String answer = response.body().answers.toString();
//                        JsonParser parser = new JsonParser();
//                        JsonElement element = parser.parse(answer);
//                        if(element.getAsJsonObject().get("answers").isJsonNull()){
//                            Toast.makeText(view.getContext(),"dd",Toast.LENGTH_SHORT).show();
//                        }

                        mContext.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<MainMyListDataAdapter.InOutBoxQuestionnaireItem> call, Throwable t) {
                            Log.v("@@@@@@@@", t.getMessage());
                    }
                });

            });
        }

        public void bind(int position, int totalCount, InboxQuestionnaireDto item) {
            if (item.questionnairesId.isEmpty()) {
                // Indicate that there is no item when question Id is "" (empty)
                tvIndex.setVisibility(View.GONE);
                tvSender.setText(R.string.main_home_no_questionnaires);

            } else {
                tvIndex.setVisibility(View.VISIBLE);
                tvIndex.setText(getPositionIndexCharSeq(position, totalCount));
                tvSender.setText(String.format(
                        tvSender.getContext().getString(R.string.main_home_name_sent_this_questions),
                        item.sender));
            }
        }

        private CharSequence getPositionIndexCharSeq(int position, int total) {
            SpannableString strPos = new SpannableString(String.valueOf(position));
            SpannableString strElse = new SpannableString("/" + total);

            strPos.setSpan(new RelativeSizeSpan(1.2f), 0, strPos.length(), 0);
            return TextUtils.concat(strPos, strElse);
        }
    }
}
