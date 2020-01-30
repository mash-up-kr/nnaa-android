package com.mashup.nnaa;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AnswerTextActivity extends AppCompatActivity {
    EditText text;
    ImageButton stopBtn;
    LinearLayout leftbtn;
    RelativeLayout rightbtn;
    ImageView heartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_text);

        text = findViewById(R.id.answer_by_texting);
        stopBtn = findViewById(R.id.stop_answer_text_btn);
        leftbtn = findViewById(R.id.undo_btn_in_answer_text);
        rightbtn = findViewById(R.id.next_btn_in_answer_text);
        heartBtn = findViewById(R.id.choice_btn_heart_answer_text);

        leftbtn.setOnClickListener((view) -> finish());
    }
}
