package com.mashup.nnaa;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mashup.nnaa.data.QuestionnaireItem;

public class AnswerTextActivity extends AppCompatActivity {
    TextView questionView;
    EditText answerView;
    ImageButton stopBtn;
    LinearLayout leftbtn;
    RelativeLayout rightbtn;
    ImageView heartBtn;
    TextView number;

    QuestionnaireItem qitem = new QuestionnaireItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_text);

        questionView = findViewById(R.id.question);
        answerView = findViewById(R.id.answer_by_texting);
        stopBtn = findViewById(R.id.stop_answer_text_btn);
        leftbtn = findViewById(R.id.undo_btn_in_answer_text);
        rightbtn = findViewById(R.id.next_btn_in_answer_text);
        heartBtn = findViewById(R.id.choice_btn_heart_answer_text);
        number = findViewById(R.id.questionnaire_number);


        /*********임시 질문**********/
        String [] temp = {"당신의 현재 기분은?"};
        qitem.setQuestions(temp);
        qitem.index = 2;


        /*******************/
        questionView.setText(qitem.getQuestion()[0]);

        if(qitem.getCompleteFlag()) {        //등록된 답변 있으면 그걸로 셋팅
            answerView.setText(qitem.getAnswer());
        }

        leftbtn.setOnClickListener(v -> {
            qitem.setAnswer(answerView.getText().toString());
            qitem.completeFlag = true;

            finish();
        });

        rightbtn.setOnClickListener(v-> {
            qitem.setAnswer(answerView.getText().toString());
            qitem.completeFlag = true;

        });
    }
}
