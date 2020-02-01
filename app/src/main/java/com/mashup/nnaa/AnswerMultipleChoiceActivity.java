package com.mashup.nnaa;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AnswerMultipleChoiceActivity extends AppCompatActivity {
    ImageButton stopBtn;
    LinearLayout leftbtn;
    RelativeLayout rightbtn;
    LinearLayout [] multiple_choice = new LinearLayout[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_multiple_choice);

        stopBtn = findViewById(R.id.stop_answer_multiple_btn);
        leftbtn = findViewById(R.id.undo_btn_in_multiple_chioce);
        rightbtn = findViewById(R.id.next_btn_in_multiple_chioce);
        multiple_choice[0] = findViewById(R.id.multiple_choice_btn_A);
        multiple_choice[1] = findViewById(R.id.multiple_choice_btn_B);
        multiple_choice[2] = findViewById(R.id.multiple_choice_btn_C);
        multiple_choice[3] = findViewById(R.id.multiple_choice_btn_D);

        leftbtn.setOnClickListener((view) -> finish());
        for(int i=0;i<4;i++)
            multiple_choice[i].setOnClickListener(new MultipleChoiceListener(this));
    }
}

class MultipleChoiceListener implements View.OnClickListener {
    AnswerMultipleChoiceActivity parent;
    MultipleChoiceListener(AnswerMultipleChoiceActivity app) {
        parent = app;
    }
    @Override
    public void onClick(View view) {
        if(view.isPressed()) {
            for(int i=0;i<4;i++)
                parent.multiple_choice[i].setSelected(false);
            view.setSelected(true);
           // Toast.makeText(parent.getApplicationContext(), view.getId() + "is selected", Toast.LENGTH_SHORT).show();
        }
    }
}
