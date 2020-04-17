package com.mashup.nnaa.question;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.QuestionControllerService;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.select.SetTypeOfFriendActivity;
import com.mashup.nnaa.reply.ReplyActivity;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.DeleteAdapter;
import com.mashup.nnaa.util.ItemTouchHelperCallback;
import com.mashup.nnaa.util.QuestionAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionActivity extends AppCompatActivity {

    private ImageView img_delete, img_add;
    private TextView txt_name, txt_type;
    private Button btn_cancel, btn_next;
    private QuestionAdapter questionAdapter;
    private List<NewQuestionDto> questionList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();

        String category = intent.getStringExtra("category");
        String type = intent.getStringExtra("type");
        String name = intent.getStringExtra("name");

        txt_name = findViewById(R.id.txt_name);
        txt_type = findViewById(R.id.txt_type);
        btn_next = findViewById(R.id.btn_next);
        btn_cancel = findViewById(R.id.btn_cancel);
        img_delete = findViewById(R.id.img_delete);
        img_add = findViewById(R.id.img_add);


        txt_type.setText(String.format("%s인 , ", type));
        txt_name.setText(String.format("%s께", name));

        btn_next.setOnClickListener(view -> {
            // 보낸 질문함으로 넘어감
            String replyname = txt_name.getText().toString();
            Intent reply_intent = new Intent(QuestionActivity.this, ReplyActivity.class);
            reply_intent.putExtra("reply_name", replyname);
            reply_intent.putExtra("category", category);
            startActivity(reply_intent);

        });


        btn_cancel.setOnClickListener(view -> {
            finish();
        });


        RecyclerView recyclerQuestion = findViewById(R.id.recycler_question);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerQuestion.setLayoutManager(linearLayoutManager);
        recyclerQuestion.setHasFixedSize(true);

        questionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(this, questionList);
        recyclerQuestion.setAdapter(questionAdapter);
        this.getQuestionRandom();


        img_delete.setOnClickListener(view -> {
            Toast.makeText(QuestionActivity.this, "질문삭제 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent deleteintent = new Intent(getApplicationContext(), DeleteQuestionActivity.class);
            deleteintent.putExtra("name", name);
            deleteintent.putExtra("type", type);
            deleteintent.putExtra("category", category);

            startActivity(deleteintent);
        });

        img_add.setOnClickListener(view -> {
            Toast.makeText(QuestionActivity.this, "질문생성 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent bookmarkintent = new Intent(QuestionActivity.this, LocalQuestionActivity.class);
            bookmarkintent.putExtra("type", type);
            bookmarkintent.putExtra("category", category);
            bookmarkintent.putExtra("name", name);

            startActivity(bookmarkintent);
        });
    }

    private void getQuestionRandom() {
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
        String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();

        Intent intent1 = new Intent(QuestionActivity.this, QuestionAdapter.class);
        intent1.putExtra("category", category);

        RetrofitHelper.getInstance().getQuestion(id, token, category, new Callback<List<NewQuestionDto>>() {
            @Override
            public void onResponse(Call<List<NewQuestionDto>> call, Response<List<NewQuestionDto>> response) {
                if (questionList != null) {
                    questionList = response.body();
                    Log.v("QuestionRandom", "Response =  " + response.code() + "," + "id:" + id + "," + "token: " + token + "," + "category: " + category +
                            response.body().get(0).getType()  + questionList.size());
                            intent1.putExtra("zero",response.body().get(0).getContent());

                    questionAdapter.setQuestionList(questionList);

                } else if (questionList.size() == 0) {
                    Toast.makeText(QuestionActivity.this, "질문을 생성해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("QuestionRandom", "No Question");
                }
            }

            @Override
            public void onFailure(Call<List<NewQuestionDto>> call, Throwable t) {
                Log.v("QuestionRandom", "에러:" + t.getMessage());
            }
        });
    }
}
