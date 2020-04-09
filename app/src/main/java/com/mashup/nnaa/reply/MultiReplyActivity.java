package com.mashup.nnaa.reply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.util.ClickCallbackListener;
import com.mashup.nnaa.util.ReplyAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultiReplyActivity extends AppCompatActivity {

    TextView reply_number;
    ImageButton reply_cancel, reply_choice;
    Button btn_next_question, btn_past;
    private ReplyAdapter replyAdapter;
    private List<NewQuestionDto> questionDtoList;
    private ClickCallbackListener clickCallbackListener = pos -> Toast.makeText(MultiReplyActivity.this, pos+"번째 아이템",Toast.LENGTH_SHORT).show();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_reply);

        reply_number = findViewById(R.id.reply_number);
        reply_cancel = findViewById(R.id.reply_cancel);
        btn_past = findViewById(R.id.btn_past);
        reply_choice = findViewById(R.id.reply_choice);
        btn_next_question = findViewById(R.id.btn_next_question);

        reply_cancel.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("답변 중단하기");
            builder.setMessage("답변을 중단하실건가요?");
            builder.setPositiveButton("네", (dialogInterface, i) -> {
                Toast.makeText(getApplicationContext(), "답변을 중단하겠습니다.", Toast.LENGTH_SHORT).show();
                finish();
            });
            builder.setNegativeButton("아니요", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "계속 답변해주세요~", Toast.LENGTH_SHORT).show());
            builder.show();
        });
        // 이전문제
        btn_past.setOnClickListener(view -> {


        });
        // 다음 문제
        btn_next_question.setOnClickListener(view -> {
        });


        RecyclerView recyclerAnswer = findViewById(R.id.recycler_answer);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        recyclerAnswer.setLayoutManager(layoutManager);
        recyclerAnswer.setHasFixedSize(true);

        // ui 키보드에 밀리는거 방지
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        questionDtoList = new ArrayList<>();
        replyAdapter = new ReplyAdapter(this, questionDtoList);
        recyclerAnswer.setAdapter(replyAdapter);

        this.answerQuestion();
    }

    private void answerQuestion() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String token = intent.getStringExtra("token");
        String category = intent.getStringExtra("category");
        RetrofitHelper.getInstance().getQuestion(id, token, category, new Callback<List<NewQuestionDto>>() {
            @Override
            public void onResponse(Call<List<NewQuestionDto>> call, Response<List<NewQuestionDto>> response) {
                if (questionDtoList != null) {
                    questionDtoList = response.body();
                    Log.v("답변 리스트", "Response =  " + response.code() + "," + "id:" + "," + "token: " + token + "," + "category: " + category);
                    replyAdapter.setQuestionDtoList(questionDtoList);
                    replyAdapter.setCallbackListener(clickCallbackListener);
                } else if (questionDtoList.size() == 0) {
                    Toast.makeText(MultiReplyActivity.this, "질문을 생성해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("답변 리스트", "No Question");
                }
            }

            @Override
            public void onFailure(Call<List<NewQuestionDto>> call, Throwable t) {
                Log.v("답변 리스트", "에러:" + t.getMessage());
            }
        });
    }

}
