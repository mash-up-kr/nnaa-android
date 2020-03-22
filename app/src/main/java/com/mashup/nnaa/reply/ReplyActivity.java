package com.mashup.nnaa.reply;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mashup.nnaa.R;
import com.mashup.nnaa.question.QuestionActivity;

public class ReplyActivity extends AppCompatActivity {

    TextView reply_name;
    Button btn_not, btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        Intent intent = getIntent();

        reply_name = findViewById(R.id.reply_name);
        btn_not = findViewById(R.id.btn_not);
        btn_start = findViewById(R.id.btn_start);

        // QuestionActivity에서 이름 받아오자
        if (intent != null && intent.getExtras() != null) {
            String replyName = intent.getStringExtra("reply name");
            reply_name.setText(replyName);
        }

        btn_not.setOnClickListener(view -> {
            finish();
        });
        btn_start.setOnClickListener(view -> {
            Intent start_intent = new Intent(ReplyActivity.this, MultiReplyActivity.class);
            startActivity(start_intent);
            Toast.makeText(ReplyActivity.this, "답변하러 가실게요!", Toast.LENGTH_SHORT).show();
        });
    }
}
