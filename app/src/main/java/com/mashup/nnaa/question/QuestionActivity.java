package com.mashup.nnaa.question;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashup.nnaa.R;
import com.mashup.nnaa.data.Choices;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.reply.ReplyActivity;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.QuestionAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {

    private ImageView img_add, img_delete;
    private TextView txt_name, txt_type;
    private EditText makeQuestion;
    private Button btn_next;
    private ImageView btn_cancel;
    private QuestionAdapter questionAdapter;
    private ArrayList<NewQuestionDto> questionList;

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
        img_add = findViewById(R.id.img_add);
        img_delete = findViewById(R.id.img_delete);
        makeQuestion = findViewById(R.id.edit_make);

        txt_type.setText(String.format("%s인 , ", type));
        txt_name.setText(String.format("%s께", name));

        makeQuestion.setOnClickListener(view -> {
            Toast.makeText(QuestionActivity.this, "질문생성 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent bookmarkintent = new Intent(QuestionActivity.this, MakeQuestionActivity.class);
            startActivityForResult(bookmarkintent, 0);
        });

        btn_next.setOnClickListener(view -> {
            // 보낸 질문함으로 넘어감
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("질문지 보내기");
            builder.setMessage("질문지를 전송할까요?");
            builder.setPositiveButton("확인", (dialogInterface, i) -> {
                Toast.makeText(getApplicationContext(), "질문지를 보내겠습니다.", Toast.LENGTH_SHORT).show();
                String replyname = txt_name.getText().toString();
                Intent reply_intent = new Intent(QuestionActivity.this, ReplyActivity.class);
                reply_intent.putExtra("reply_name", replyname);
                reply_intent.putExtra("category", category);
                startActivity(reply_intent);
            });
            builder.setNegativeButton("취소", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "질문지 보내기 취소", Toast.LENGTH_SHORT).show());
            builder.show();
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

        img_add.setOnClickListener(view -> {
            Toast.makeText(QuestionActivity.this, "즐겨찾기 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent bookmarkintent = new Intent(QuestionActivity.this, FavoritesActivity.class);
            startActivity(bookmarkintent);
        });

        img_delete.setOnClickListener(view -> {
            Toast.makeText(QuestionActivity.this, "질문삭제 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent deleteintent = new Intent(view.getContext(), DeleteQuestionActivity.class);
            deleteintent.putExtra("name", name);
            deleteintent.putExtra("type", type);
            deleteintent.putExtra("list", questionList);

            startActivityForResult(deleteintent, 1000);
        });
    }

    private void getQuestionRandom() {
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
        String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();

        Intent intent1 = new Intent(QuestionActivity.this, QuestionAdapter.class);
        intent1.putExtra("category", category);

        RetrofitHelper.getInstance().getQuestion(id, token, category, new Callback<ArrayList<NewQuestionDto>>() {
            @Override
            public void onResponse(Call<ArrayList<NewQuestionDto>> call, Response<ArrayList<NewQuestionDto>> response) {
                if (questionList != null) {
                    questionList = response.body();
                    questionAdapter.setQuestionList(questionList);

                    for (NewQuestionDto newQuestionDto : getQuestion()) {
                        questionList.add(newQuestionDto);
                        questionAdapter.notifyDataSetChanged();
                    }
                } else if (questionList.size() == 0) {
                    Toast.makeText(QuestionActivity.this, "질문을 생성해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("QuestionRandom", "No Question");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NewQuestionDto>> call, Throwable t) {
                Log.v("QuestionRandom", "에러:" + t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case MakeQuestionActivity.RESULT_UPDATE_OK:

                Intent intent1 = getIntent();
                String category = intent1.getStringExtra("category");

                String contents = data.getStringExtra("content");
                String type = data.getStringExtra("type");
                String a = data.getStringExtra("setA");
                String b = data.getStringExtra("setB");
                String c = data.getStringExtra("setC");
                String d = data.getStringExtra("setD");

                Choices choices = new Choices();
                choices.setA(a);
                choices.setB(b);
                choices.setC(c);
                choices.setD(d);

                if (contents != null && !contents.isEmpty()) {
                    NewQuestionDto newQuestionDto = new NewQuestionDto("", contents, category, type, choices, false);

                    questionList.add(newQuestionDto);
                    setQuestion(questionList);


                    Log.v("Question Add", "질문생성: " + "content: " + contents + "," + "category: " + category + "," +
                            "type: " + type + "," + "choices: " + "[a]:" + a + "," + "[b]:" + b + "," + "[c]:" + c + "," + "[d]:" + d);

                    questionAdapter.notifyDataSetChanged();
                }
                break;

            case DeleteQuestionActivity.RESULT_DELETE_OK:

                questionList = (ArrayList<NewQuestionDto>) data.getSerializableExtra("delete");
                setQuestion(questionList);
                questionAdapter.setQuestionList(questionList);

                break;

        }
    }

    private void setQuestion(ArrayList<NewQuestionDto> localList) {
        SharedPreferences prefs = getSharedPreferences("list", 0);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(localList);
        editor.putString("question", json);
        editor.apply();
    }

    private ArrayList<NewQuestionDto> getQuestion() {
        SharedPreferences prefs = getSharedPreferences("list", 0);
        Gson gson = new Gson();
        String json = prefs.getString("question", "");
        Type type = new TypeToken<ArrayList<NewQuestionDto>>() {
        }.getType();
        ArrayList<NewQuestionDto> list = gson.fromJson(json, type);
        return list;
    }
}
