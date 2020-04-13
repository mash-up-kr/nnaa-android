package com.mashup.nnaa.reply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.ClickCallbackListener;
import com.mashup.nnaa.util.ReplyAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultiReplyActivity extends AppCompatActivity {

    TextView reply_number, reply_end_nubmer, txtA, txtB, txtC, txtD, veryYes, Yes, No, veryNo;
    ImageButton reply_cancel, reply_choice, reply_O, reply_X, btnA, btnB, btnC, btnD;
    ImageView ox_bar, multi_img1, multi_img2, multi_img3, multi_img4;
    Button btn_next_question, btn_past;
    EditText replyEdit;
    private ReplyAdapter replyAdapter;
    private List<NewQuestionDto> questionDtoList;
    private ClickCallbackListener clickCallbackListener = pos -> Toast.makeText(MultiReplyActivity.this, pos + "번째 아이템", Toast.LENGTH_SHORT).show();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_reply);

        reply_number = findViewById(R.id.reply_number);
        txtA = findViewById(R.id.txt_A);
        txtB = findViewById(R.id.txt_B);
        txtC = findViewById(R.id.txt_C);
        txtD = findViewById(R.id.txt_D);
        veryYes = findViewById(R.id.mutli_txt1);
        Yes = findViewById(R.id.mutli_txt2);
        No = findViewById(R.id.mutli_txt3);
        veryNo = findViewById(R.id.mutli_txt4);
        btnA = findViewById(R.id.multi_btn_a);
        btnB = findViewById(R.id.multi_btn_b);
        btnC = findViewById(R.id.multi_btn_c);
        btnD = findViewById(R.id.multi_btn_d);
        multi_img1 = findViewById(R.id.multi_img1);
        multi_img2 = findViewById(R.id.multi_img2);
        multi_img3 = findViewById(R.id.multi_img3);
        multi_img4 = findViewById(R.id.multi_img4);
        reply_O = findViewById(R.id.reply_o_btn);
        reply_X = findViewById(R.id.reply_x_btn);
        ox_bar = findViewById(R.id.reply_ox_bar);
        reply_end_nubmer = findViewById(R.id.reply_end_number);
        reply_cancel = findViewById(R.id.reply_cancel);
        btn_past = findViewById(R.id.btn_past);
        reply_choice = findViewById(R.id.reply_choice);
        btn_next_question = findViewById(R.id.btn_next_question);
        replyEdit = findViewById(R.id.reply_edit);

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

        // 질문 즐겨찾기
        reply_choice.setOnClickListener(view -> {
            reply_choice.setImageResource(R.drawable.choice_btn_heart_on);
            Toast.makeText(view.getContext(), "즐겨찾기 추가", Toast.LENGTH_SHORT).show();
        });

        RecyclerView recyclerAnswer = findViewById(R.id.recycler_answer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
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
        String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
        String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();
        String category = intent.getStringExtra("category");
        if (category.equals("주관식")) {

        } else if (category.equals("OX")) {

        } else if (category.equals("객관식")) {

        }
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
