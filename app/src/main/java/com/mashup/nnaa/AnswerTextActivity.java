package com.mashup.nnaa;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mashup.nnaa.data.QuestionnaireItem;
import com.mashup.nnaa.main.MainActivity;

public class AnswerTextActivity extends AppCompatActivity {
    TextView question_num;
    TextView questionView;
    EditText answerView;
    ImageButton stopbtn;
    LinearLayout leftbtn;
    RelativeLayout rightbtn;
    ImageView heartbtn;

    QuestionnaireItem qitem = new QuestionnaireItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_text);

        question_num = findViewById(R.id.questionnaire_number_in_text);
        questionView = findViewById(R.id.question);
        answerView = findViewById(R.id.answer_by_texting);
        stopbtn = findViewById(R.id.stop_answer_text_btn);
        leftbtn = findViewById(R.id.undo_btn_in_answer_text);
        rightbtn = findViewById(R.id.next_btn_in_answer_text);
        heartbtn = findViewById(R.id.choice_btn_heart_answer_text);

        heartbtn.setSelected(false);

        /*********임시 질문**********/
        String [] temp = {"당신의 현재 기분은?"};
        qitem.setQuestions(temp);
        qitem.index = 9;


        /*********질문 셋팅**********/
        //question
        questionView.setText(qitem.getQuestion()[0]);
        //index
        if(qitem.getIndex()<10 && qitem.getIndex()>=0) {
            String qNum = "0" + Integer.toString(qitem.getIndex()) + "/" + "20";
            question_num.setText(qNum);
        }
        else {
            String qNum = Integer.toString(qitem.getIndex()) + "/" + "20";
            question_num.setText(qNum);

        }

        // 임시 저장된 답변 있는 경우
        if(qitem.getCompleteFlag()) {
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

        // alert dialog
        stopbtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("경고어쩌구");
            builder.setMessage("답변을 중단하고 메인화면으로 돌아가시겠습니까?\n 답변은 저장되지 않습니다.");
            builder.setPositiveButton("예",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
            builder.setNegativeButton("아니오",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) { }
                    });
            builder.show();
        });

        heartbtn.setOnClickListener(v -> {
            if(heartbtn.isSelected()) {
                heartbtn.setSelected(false);
                Toast.makeText(this.getApplicationContext(), "즐겨찾기를 해제했습니다", Toast.LENGTH_SHORT).show();
            }
            else {
                heartbtn.setSelected(true);
                Toast.makeText(this.getApplicationContext(), "질문을 즐겨찾기 했습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
