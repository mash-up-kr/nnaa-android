package com.mashup.nnaa.AnswerActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mashup.nnaa.R;

public class AnswerActivity extends AppCompatActivity {

    RecyclerView rvAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        rvAnswer = findViewById(R.id.rvAnswer);
        rvAnswer.setAdapter(new AnswerAdapter());
        rvAnswer.setLayoutManager(new LinearLayoutManager(this));

        for(int i=0; i<30;i++){
            AnswerItem item = new AnswerItem();
            item.setQuest(i + " question");
            //item.setAnswer(i + " answer");
            ((AnswerAdapter)rvAnswer.getAdapter()).addItem(item);
        }
    }
}
