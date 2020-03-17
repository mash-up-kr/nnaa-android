package com.mashup.nnaa;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AnswerOxChoiceActivity extends AppCompatActivity {
    ImageButton stopBtn;
    LinearLayout leftBtn;
    RelativeLayout rightBtn;
    ImageView [] oxChoice = new ImageView[2];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_ox_choice);

        stopBtn =findViewById(R.id.stop_answer_ox_btn);
        leftBtn = findViewById(R.id.undo_btn_in_ox_chioce);
        rightBtn = findViewById(R.id.next_btn_in_ox_chioce);
        oxChoice[0] = findViewById(R.id.ox_choice_btn_O);
        oxChoice[1] = findViewById(R.id.ox_choice_btn_X);

        leftBtn.setOnClickListener((view) -> finish());

        for(int i=0;i<2;i++)
            oxChoice[i].setOnClickListener(new OxChoiceListener(this));

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