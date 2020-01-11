package com.mashup.nnaa;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AnswerMultipleChoiceActivity extends AppCompatActivity {
    TextView stopBtnText;
    ImageButton stopBtn;
    LinearLayout leftbtn;
    RelativeLayout rightbtn;
    LinearLayout [] multiple_choice = new LinearLayout[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_multiple_choice);

        //stopBtnText = findViewById(R.id.stop_answer_multiple_text);
        //stopBtn = findViewById(R.id.stop_answer_multiple);

        leftbtn = findViewById(R.id.undo_btn_in_multiple_chioce);
        rightbtn = findViewById(R.id.next_btn_in_multiple_chioce);

        leftbtn.setOnClickListener((view) -> finish());

        multiple_choice[0] = findViewById(R.id.multiple_choice_btn_A);
        multiple_choice[1] = findViewById(R.id.multiple_choice_btn_B);
        multiple_choice[2] = findViewById(R.id.multiple_choice_btn_C);
        multiple_choice[3] = findViewById(R.id.multiple_choice_btn_D);

        for(int i=0;i<4;i++)
            multiple_choice[i].setOnClickListener(new MultipleChoiceListener(this));
    }
}

class MultipleChoiceListener implements View.OnClickListener {
    AppCompatActivity parent;
    MultipleChoiceListener(AppCompatActivity app) {
        parent = app;
    }
    @Override
    public void onClick(View view) {
        Toast.makeText(parent.getApplicationContext(), view.getId()+"is clicked",Toast.LENGTH_SHORT).show();
    }
}
