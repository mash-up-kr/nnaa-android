package com.mashup.nnaa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mashup.nnaa.data.QuestionnaireItem;
import com.mashup.nnaa.main.MainActivity;

public class AnswerMultipleChoiceActivity extends AppCompatActivity {
    TextView question;
    TextView question_num;
    ImageButton stopbtn;
    LinearLayout leftbtn;
    RelativeLayout rightbtn;
    ImageView heartbtn;
    LinearLayout [] multiple_choice = new LinearLayout[4];
    TextView [] multiple_choice_text = new TextView[4];

    QuestionnaireItem qitem = new QuestionnaireItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_multiple_choice);

        question = findViewById(R.id.question_multiple_choice);
        question_num = findViewById(R.id.questionnaire_number_in_multiple);
        stopbtn = findViewById(R.id.stop_answer_multiple_btn);
        leftbtn = findViewById(R.id.undo_btn_in_multiple_chioce);
        rightbtn = findViewById(R.id.next_btn_in_multiple_chioce);
        heartbtn = findViewById(R.id.choice_btn_heart_in_multiple);
        multiple_choice[0] = findViewById(R.id.multiple_choice_btn_A);
        multiple_choice[1] = findViewById(R.id.multiple_choice_btn_B);
        multiple_choice[2] = findViewById(R.id.multiple_choice_btn_C);
        multiple_choice[3] = findViewById(R.id.multiple_choice_btn_D);
        multiple_choice_text[0] = findViewById(R.id.multiple_choice_btn_A_text);
        multiple_choice_text[1] = findViewById(R.id.multiple_choice_btn_B_text);
        multiple_choice_text[2] = findViewById(R.id.multiple_choice_btn_C_text);
        multiple_choice_text[3] = findViewById(R.id.multiple_choice_btn_D_text);

        heartbtn.setSelected(false);

        /********임시 질문, 나중에 qitem에 질문 가져옴********/
        qitem.index = 5;
        String [] tmp = {"현재 삶에 만족하나요?","ㅇㅇㅇㅇ","ㅇ","ㄴ","ㄴㄴㄴㄴ"};
        qitem.setQuestions(tmp);


        /************질문 셋팅************/
        //question
        question.setText(qitem.getQuestion()[0]);
        for(int i=0;i<4;i++) {
            multiple_choice_text[i].setText(qitem.getQuestion()[i+1]);
        }
        //index
        if(qitem.getIndex()<10 && qitem.getIndex()>=0) {
            String qNum = "0" + Integer.toString(qitem.getIndex()) + "/" + "20";
            question_num.setText(qNum);
        }
        else {
            String qNum = Integer.toString(qitem.getIndex()) + "/" + "20";
            question_num.setText(qNum);

        }

        //  category="MULTIPLE", answer="A","B","C","D"
        //  이미 선택된 거 있는 경우 (뒤에 질문 했다가 다시 앞으로 돌아온 경우)
        if(qitem.getCompleteFlag()) {
            for (int j = 0; j < 4; j++)
                multiple_choice[j].setSelected(false);
            switch(qitem.getAnswer()) {
                case "A": multiple_choice[0].setSelected(true); break;
                case "B": multiple_choice[1].setSelected(true); break;
                case "C": multiple_choice[2].setSelected(true); break;
                case "D": multiple_choice[3].setSelected(true); break;
            }
        }

        /************버튼 클릭************/
        for(int i=0;i<4;i++)
            multiple_choice[i].setOnClickListener(view -> {
                if(view.isPressed()) {
                    for (int j = 0; j < 4; j++)
                        multiple_choice[j].setSelected(false);
                    view.setSelected(true);

                    qitem.completeFlag = true;
                }
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


        leftbtn.setOnClickListener(v -> finish());
        rightbtn.setOnClickListener(v -> { });
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
