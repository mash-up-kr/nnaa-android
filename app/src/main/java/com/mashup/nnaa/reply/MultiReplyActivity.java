package com.mashup.nnaa.reply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.QuestionItem;
import com.mashup.nnaa.data.ReplyMultiItem;
import com.mashup.nnaa.data.ReplyOxItem;
import com.mashup.nnaa.data.ReplySubjectItem;
import com.mashup.nnaa.util.ReplyMultiAdapter;
import com.mashup.nnaa.util.ReplyOxAdapter;
import com.mashup.nnaa.util.ReplySubjectAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MultiReplyActivity extends AppCompatActivity {

    TextView reply_number, reply_question_name;
    ImageButton reply_cancel, reply_choice, btn_past;
    Button btn_next_question;
    private ReplySubjectAdapter subjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_reply);

        reply_number = findViewById(R.id.reply_number);
        reply_question_name = findViewById(R.id.reply_question_name);
        reply_cancel = findViewById(R.id.reply_cancel);
        btn_past = findViewById(R.id.btn_past);
        reply_choice = findViewById(R.id.reply_choice);
        btn_next_question = findViewById(R.id.btn_next_question);

        btn_past.setOnClickListener(view -> {
            Intent pastintent = new Intent(getApplicationContext(), ReplyActivity.class);
            startActivity(pastintent);
        });

        reply_cancel.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("답변 중단하기");
            builder.setMessage("답변을 중단하실건가요?");
            builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "답변을 중단하겠습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ReplyActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(),"계속 답변해주세요~",Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        });

        btn_next_question.setOnClickListener(view-> {

        });

        init();
        getSubjectItem();

    }

    private void init() {
        RecyclerView subjectRecyclerview = findViewById(R.id.recycler_multi_reply);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        subjectRecyclerview.setLayoutManager(linearLayoutManager);

        subjectAdapter = new ReplySubjectAdapter();
        subjectRecyclerview.setAdapter(subjectAdapter);
        // ui 키보드에 밀리는거 방지
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


    }
    private void getSubjectItem() {
        List<String> listInitial = Collections.singletonList("답변을 입력해주세요");

        for (int i = 0; i < listInitial.size(); i++) {

            ReplySubjectItem replySubjectItem = new ReplySubjectItem();
            replySubjectItem.setSubject_edit(listInitial.get(i));

            subjectAdapter.addItem(replySubjectItem);
        }
        subjectAdapter.notifyDataSetChanged();
    }
}
