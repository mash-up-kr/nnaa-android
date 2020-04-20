package com.mashup.nnaa.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.question.LocalQuestionActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.LayoutInflater.from;

public class LocalQuestionAdapter extends RecyclerView.Adapter<LocalQuestionAdapter.ViewHolder> {

    private ArrayList<NewQuestionDto> list;
    private Context mContext;
    private String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
    private String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();

    public LocalQuestionAdapter(Context context, ArrayList<NewQuestionDto> list) {
        this.mContext = context;
        this.list = list;
    }

    public void setLocalQuestionList(ArrayList<NewQuestionDto> questionList) {
        this.list = questionList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocalQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = from(parent.getContext()).inflate(R.layout.local_question_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalQuestionAdapter.ViewHolder holder, int position) {
        holder.local_content.setText(list.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView local_content;
        private CheckBox bookmark;

        ViewHolder(View itemView) {
            super(itemView);

            local_content = itemView.findViewById(R.id.local_question);
            bookmark = itemView.findViewById(R.id.local_choice);

            bookmark.setOnCheckedChangeListener(null);
            bookmark.setOnCheckedChangeListener((buttonView, isChecked) -> {

                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    NewQuestionDto item = list.get(pos);

                    NewQuestionDto newQuestionDto = new NewQuestionDto(item.getId(), item.getContent(), item.getCategory(), item.getType(), item.getChoices());

                    if (bookmark.isChecked()) {
                        // 북마크 등록
                        RetrofitHelper.getInstance().favoriteEnroll(id, token, newQuestionDto, new Callback<NewQuestionDto>() {
                            @Override
                            public void onResponse(Call<NewQuestionDto> call, Response<NewQuestionDto> response) {
                                bookmark.setChecked(true);
                                Log.v("bookmark", "response:" + response.code() + item.getContent() + item.getId());

                            }

                            @Override
                            public void onFailure(Call<NewQuestionDto> call, Throwable t) {
                                Log.v("Local bookmark", t.getMessage());
                            }
                        });
                    } else {
                        // 북마크 해제
                        RetrofitHelper.getInstance().favoriteDelete(id, token, item.getId(), new Callback<NewQuestionDto>() {
                            @Override
                            public void onResponse(Call<NewQuestionDto> call, Response<NewQuestionDto> response) {

                                Log.v("bookmark delete", "response:" + response.code() + item.getContent() + item.getId());
                                bookmark.setChecked(false);
                            }

                            @Override
                            public void onFailure(Call<NewQuestionDto> call, Throwable t) {
                                Log.v("bookmark delete", t.getMessage());
                            }
                        });
                    }
                }
            });
        }
    }
}
