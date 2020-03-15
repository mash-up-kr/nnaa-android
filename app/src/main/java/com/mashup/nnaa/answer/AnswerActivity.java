package com.mashup.nnaa.answer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mashup.nnaa.R;

public class AnswerActivity extends AppCompatActivity {
    LinearLayout leftBtn;
    RelativeLayout rightBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent =getIntent();
        setContentView(R.layout.activity_answer);

        leftBtn = findViewById(R.id.undo_btn_in_answer);
        rightBtn = findViewById(R.id.next_btn_in_answer);
        leftBtn.setOnClickListener((view) -> finish());

        rightBtn.setOnClickListener(view -> {
                   Intent intent1 = new Intent(AnswerActivity.this, AnswerMultipleChoiceActivity.class);
                   startActivity(intent1);
        });

    }
}
