package com.mashup.nnaa.answer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.QuestionnaireItem;
import com.mashup.nnaa.main.MainActivity;

public class AnswerOxChoiceActivity extends AppCompatActivity {
    ImageButton stopBtn;
    LinearLayout leftBtn;
    RelativeLayout rightBtn;
    TextView question;
    TextView question_num;
    ImageView heartbtn;
    ImageView[] oxChoice = new ImageView[2];
    QuestionnaireItem qitem = new QuestionnaireItem();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_ox_choice);

        stopBtn = findViewById(R.id.stop_answer_ox_btn);
        leftBtn = findViewById(R.id.undo_btn_in_ox_chioce);
        rightBtn = findViewById(R.id.next_btn_in_ox_chioce);
        oxChoice[0] = findViewById(R.id.ox_choice_btn_O);
        oxChoice[1] = findViewById(R.id.ox_choice_btn_X);
        question = findViewById(R.id.question_ox_choice);
        question_num = findViewById(R.id.questionnaire_number_in_ox);
        heartbtn = findViewById(R.id.choice_btn_heart_in_ox);

        heartbtn.setSelected(false);

        /*
        for(int i=0;i<2;i++)
            oxChoice[i].setOnClickListener(new com.mashup.nnaa.answer.OxChoiceListener(this));
        */

        //임시 질문 생성(임시 코드) + 나중에 qitem에 질문 가져오기
        qitem.index = 5;
        String[] tmp = {"ox질문1", "ox질문2", "ox질문3", "ox질문4", "ox질문5"};
        qitem.setQuestions(tmp);

        //질문
        question.setText(qitem.getQuestion()[0]);

        //질문 index 표시 + 나중에 총 질문 갯수도 받아와야 함
        if (qitem.getIndex() < 10 && qitem.getIndex() >= 0) {
            String qNum = "0" + Integer.toString(qitem.getIndex()) + "/" + "20";
            question_num.setText(qNum);
        } else {
            String qNum = Integer.toString(qitem.getIndex()) + "/" + "20";
            question_num.setText(qNum);
        }

        //  category="OX", answer="O","X"
        //  이미 선택된 거 있는 경우 (뒤에 질문 했다가 다시 앞으로 돌아온 경우)
        if (qitem.getCompleteFlag()) {
            for (int j = 0; j < 2; j++)
                oxChoice[j].setSelected(false);
            switch (qitem.getAnswer()) {
                case "A":
                    oxChoice[0].setSelected(true);
                    break;
                case "B":
                    oxChoice[1].setSelected(true);
                    break;
            }
        }

        /************버튼 클릭 + 색 변경 - 레이아웃에 추가해라 나야 */
        for (int i = 0; i < 2; i++)
            oxChoice[i].setOnClickListener(view -> {
                if (view.isPressed()) {
                    for (int j = 0; j < 2; j++)
                        oxChoice[j].setSelected(false);
                    view.setSelected(true);
                    qitem.completeFlag = true;
                }
            });

        // alert dialog
        stopBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("답변 중단 클릭 메세지");
            builder.setMessage("답변을 중단하시겠습니까?\n답변은 저장되지 않습니다.");
            builder.setPositiveButton("예",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
            builder.setNegativeButton("아니오",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.show();
        });

        leftBtn.setOnClickListener((view) -> finish());
        rightBtn.setOnClickListener(v -> {
        });
        heartbtn.setOnClickListener(v -> {
            if (heartbtn.isSelected()) {
                heartbtn.setSelected(false);
                Toast.makeText(this.getApplicationContext(), "즐겨찾기를 해제했습니다", Toast.LENGTH_SHORT).show();
            } else {
                heartbtn.setSelected(true);
                Toast.makeText(this.getApplicationContext(), "질문을 즐겨찾기 했습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class OxChoiceListener implements View.OnClickListener { //(현선)이 클래스 이해하기
    AnswerOxChoiceActivity parent;
    OxChoiceListener(AnswerOxChoiceActivity app) { parent = app; }
    @Override
    public void onClick(View view) {
        if(view.isPressed()) {
            for(int i=0;i<2;i++)
                parent.oxChoice[i].setSelected(false);
            view.setSelected(true);
            // Toast.makeText(parent.getApplicationContext(), view.getId() + "is selected", Toast.LENGTH_SHORT).show();
        }
    }
}