package com.mashup.nnaa;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mashup.nnaa.data.QuestionnaireItem;

public class AnswerOxChoiceActivity extends AppCompatActivity {
    ImageButton stopBtn;
    LinearLayout leftBtn;
    RelativeLayout rightBtn;
    TextView question_num;
    ImageView heartbtn;
    ImageView [] oxChoice = new ImageView[2];
    QuestionnaireItem qitem = new QuestionnaireItem();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_ox_choice);

        stopBtn =findViewById(R.id.stop_answer_ox_btn);
        leftBtn = findViewById(R.id.undo_btn_in_ox_chioce);
        rightBtn = findViewById(R.id.next_btn_in_ox_chioce);
        question_num = findViewById(R.id.questionnaire_number_in_ox);
        heartbtn = findViewById(R.id.choice_btn_heart_in_ox);
        oxChoice[0] = findViewById(R.id.ox_choice_btn_O);
        oxChoice[1] = findViewById(R.id.ox_choice_btn_X);

        heartbtn.setSelected(false);

        //나중에 qitem에 질문 가져오기
        qitem.index = 5;
        String [] tmp = {"ox다시 태어나도 아빠랑 결혼?","ox질문2","ox질문3","ox질문4","ox질문5"};
        qitem.setQuestions(tmp);

        //index 표시 + 나중에 총 질문 갯수도 받아와야함
        if(qitem.getIndex()<10 && qitem.getIndex()>=0) {
            String qNum = "0" + Integer.toString(qitem.getIndex()) + "/" + "20";
            question_num.setText(qNum);
        }
        else {
            String qNum = Integer.toString(qitem.getIndex()) + "/" + "20";
            question_num.setText(qNum);
        }

        //  category="OX", answer="O","X"
        //  이미 선택된 거 있는 경우 (뒤에 질문 했다가 다시 앞으로 돌아온 경우)
        if(qitem.getCompleteFlag()) {
            for (int j = 0; j < 2; j++)
                oxChoice[j].setSelected(false);
            switch(qitem.getAnswer()) {
                case "A": oxChoice[0].setSelected(true); break;
                case "B": oxChoice[1].setSelected(true); break;
            }
        }

        /************버튼 클릭************/
        for(int i=0;i<2;i++)
            oxChoice[i].setOnClickListener(view -> {
                if(view.isPressed()) {
                    for (int j = 0; j < 2; j++)
                        oxChoice[j].setSelected(false);
                    view.setSelected(true);
                    qitem.completeFlag = true;
                }
            });
    }
}

class OxChoiceListener implements View.OnClickListener {
    AnswerOxChoiceActivity parent;
    OxChoiceListener(AnswerOxChoiceActivity app) {
        parent = app;
    }
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