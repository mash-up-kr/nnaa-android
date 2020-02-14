package com.mashup.nnaa;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AnswerActivity extends AppCompatActivity {
    LinearLayout leftBtn;
    RelativeLayout rightBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        leftBtn = findViewById(R.id.undo_btn_in_answer);
        rightBtn = findViewById(R.id.next_btn_in_answer);
        leftBtn.setOnClickListener((view) -> finish());

    }
}
