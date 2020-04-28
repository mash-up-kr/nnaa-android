package com.mashup.nnaa.reply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.ReplyAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultiReplyActivity extends AppCompatActivity {

    TextView reply_number, reply_end_nubmer, txtA, txtB, txtC, txtD, veryYes, Yes, No, veryNo, txtQuestion;
    ImageButton reply_cancel, reply_choice, reply_O, reply_X, btnA, btnB, btnC, btnD;
    ImageView ox_bar, multi_img1, multi_img2, multi_img3, multi_img4, btn_past;
    Button btn_next_question;
    EditText replyEdit;
    ScrollView scrollView;
    private ReplyAdapter replyAdapter;
    private ArrayList<NewQuestionDto> questionDtoList;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_reply);

        txtQuestion = findViewById(R.id.text_question);
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

        scrollView = findViewById(R.id.scroll_view);

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

        // ui 키보드에 밀리는거 방지
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        questionDtoList = new ArrayList<>();
        this.answerQuestion();
    }

    private void answerQuestion() {
        Intent intent = getIntent();
        String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
        String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();
        String category = intent.getStringExtra("category");

        RetrofitHelper.getInstance().getQuestion(id, token, category, 15,new Callback<ArrayList<NewQuestionDto>>() {
            @Override
            public void onResponse(Call<ArrayList<NewQuestionDto>> call, Response<ArrayList<NewQuestionDto>> response) {
                if (questionDtoList != null) {
                    questionDtoList = response.body();

                    reply_number.setText(String.valueOf(count));
                    txtQuestion.setText(response.body().get(count - 1).getContent());
                    btn_next_question.setOnClickListener(view -> {
                        count++;
                        reply_number.setText(String.valueOf(count));
                        btn_next_question.setEnabled(true);
                        btn_past.setEnabled(true);
                        txtQuestion.setText(response.body().get(count - 1).getContent());
                        if (response.body().get(count - 1).getType() != null && response.body().get(count-1).isBookmarked() ==true) {
                            reply_choice.setBackgroundResource(R.drawable.choice_btn_heart_on);

                            if (response.body().get(count - 1).getType().equals("주관식")) {
                                subjectQuestion();
                            } else if (response.body().get(count - 1).getType().equals("OX")) {
                                oxQuestion();
                            } else if (response.body().get(count - 1).getType().equals("객관식")) {
                                chooesQuestion();
                            }
                        }
                        if (count == questionDtoList.size()) {
                            Toast.makeText(MultiReplyActivity.this, "마지막 질문입니다!", Toast.LENGTH_SHORT).show();
                            btn_next_question.setEnabled(false);
                            btn_past.setEnabled(true);
                        }
                    });
                    btn_past.setOnClickListener(view -> {
                        if (count == 1) {
                            btn_past.setEnabled(false);
                            btn_next_question.setEnabled(true);
                            Toast.makeText(MultiReplyActivity.this, "첫번째 질문입니다!", Toast.LENGTH_SHORT).show();
                        } else {
                            count--;
                            reply_number.setText(String.valueOf(count));
                            btn_past.setEnabled(true);
                            btn_next_question.setEnabled(true);
                            txtQuestion.setText(response.body().get(count - 1).getContent());
                            if (response.body().get(count - 1).getType() != null && response.body().get(count-1).isBookmarked() ==true) {
                                reply_choice.setBackgroundResource(R.drawable.choice_btn_heart_on);

                                if (response.body().get(count - 1).getType().equals("주관식")) {
                                    subjectQuestion();
                                } else if (response.body().get(count - 1).getType().equals("OX")) {
                                    oxQuestion();
                                } else if (response.body().get(count - 1).getType().equals("객관식")) {
                                    chooesQuestion();
                                }
                            }
                        }
                    });
                    reply_end_nubmer.setText(String.valueOf(questionDtoList.size()));
                    Log.v("답변 리스트", "Response =  " + response.code() + "," + "id:" + "," + "token: " + token);
                    /*   replyAdapter.setQuestionDtoList(questionDtoList);*/
                } else if (questionDtoList.size() == 0) {
                    Toast.makeText(MultiReplyActivity.this, "질문을 생성해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("답변 리스트", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NewQuestionDto>> call, Throwable t) {
                Log.v("답변 리스트", "에러:" + t.getMessage());
            }
        });
    }

    private void subjectQuestion() {
        replyEdit.setVisibility(View.VISIBLE);
        reply_X.setVisibility(View.INVISIBLE);
        reply_O.setVisibility(View.INVISIBLE);
        ox_bar.setVisibility(View.INVISIBLE);
        txtA.setVisibility(View.INVISIBLE);
        txtB.setVisibility(View.INVISIBLE);
        txtC.setVisibility(View.INVISIBLE);
        txtD.setVisibility(View.INVISIBLE);
        veryYes.setVisibility(View.INVISIBLE);
        Yes.setVisibility(View.INVISIBLE);
        No.setVisibility(View.INVISIBLE);
        veryNo.setVisibility(View.INVISIBLE);
        btnA.setVisibility(View.INVISIBLE);
        btnB.setVisibility(View.INVISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        btnC.setVisibility(View.INVISIBLE);
        btnD.setVisibility(View.INVISIBLE);
        multi_img1.setVisibility(View.INVISIBLE);
        multi_img2.setVisibility(View.INVISIBLE);
        multi_img3.setVisibility(View.INVISIBLE);
        multi_img4.setVisibility(View.INVISIBLE);
    }

    private void chooesQuestion() {
        txtA.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        txtB.setVisibility(View.VISIBLE);
        txtC.setVisibility(View.VISIBLE);
        txtD.setVisibility(View.VISIBLE);
        veryYes.setVisibility(View.VISIBLE);
        Yes.setVisibility(View.VISIBLE);
        No.setVisibility(View.VISIBLE);
        veryNo.setVisibility(View.VISIBLE);
        btnA.setVisibility(View.VISIBLE);
        btnB.setVisibility(View.VISIBLE);
        btnC.setVisibility(View.VISIBLE);
        btnD.setVisibility(View.VISIBLE);
        multi_img1.setVisibility(View.VISIBLE);
        multi_img2.setVisibility(View.VISIBLE);
        multi_img3.setVisibility(View.VISIBLE);
        multi_img4.setVisibility(View.VISIBLE);
        replyEdit.setVisibility(View.INVISIBLE);
        reply_X.setVisibility(View.INVISIBLE);
        reply_O.setVisibility(View.INVISIBLE);
        ox_bar.setVisibility(View.INVISIBLE);
    }

    private void oxQuestion() {
        reply_X.setVisibility(View.VISIBLE);
        reply_O.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        ox_bar.setVisibility(View.VISIBLE);
        replyEdit.setVisibility(View.INVISIBLE);
        txtA.setVisibility(View.INVISIBLE);
        txtB.setVisibility(View.INVISIBLE);
        txtC.setVisibility(View.INVISIBLE);
        txtD.setVisibility(View.INVISIBLE);
        veryYes.setVisibility(View.INVISIBLE);
        Yes.setVisibility(View.INVISIBLE);
        No.setVisibility(View.INVISIBLE);
        veryNo.setVisibility(View.INVISIBLE);
        btnA.setVisibility(View.INVISIBLE);
        btnB.setVisibility(View.INVISIBLE);
        btnC.setVisibility(View.INVISIBLE);
        btnD.setVisibility(View.INVISIBLE);
        multi_img1.setVisibility(View.INVISIBLE);
        multi_img2.setVisibility(View.INVISIBLE);
        multi_img3.setVisibility(View.INVISIBLE);
        multi_img4.setVisibility(View.INVISIBLE);
    }
}
