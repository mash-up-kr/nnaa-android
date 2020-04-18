package com.mashup.nnaa.question;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.Choices;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.util.LocalQuestionAdapter;

import java.util.ArrayList;
import java.util.List;

public class LocalQuestionActivity extends AppCompatActivity {

    private EditText makeQuestion;
    private LocalQuestionAdapter localQuestionAdapter;
    private List<NewQuestionDto> localList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_question);

        Intent intent1 = getIntent();
        String category = intent1.getStringExtra("category");

        makeQuestion = findViewById(R.id.local_mk_edit);

        RecyclerView local_recycler = findViewById(R.id.local_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        local_recycler.setLayoutManager(linearLayoutManager);
        local_recycler.setHasFixedSize(true);

        localList = new ArrayList<>();
        localQuestionAdapter = new LocalQuestionAdapter(this, localList);
        local_recycler.setAdapter(localQuestionAdapter);

        makeQuestion.setFocusable(false);
        makeQuestion.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MakeQuestionActivity.class);
            intent.putExtra("category", category);
            startActivityForResult(intent, 0);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case MakeQuestionActivity.RESULT_UPDATE_OK:
                String contents = data.getStringExtra("content");
                String category = data.getStringExtra("category");
                String type = data.getStringExtra("type");
                String a = data.getStringExtra("setA");
                String b = data.getStringExtra("setB");
                String c = data.getStringExtra("setC");
                String d = data.getStringExtra("setD");
                if (contents != null && !contents.isEmpty()) {
                    Choices choices = new Choices();
                    NewQuestionDto newQuestionDto = new NewQuestionDto("", contents, "", "", choices);
                    localList.add(newQuestionDto);
                    Log.v("데이터 추가", "질문생성: " + "content: " + contents + "," + "category: " + category + "," +
                            "type: " + type + "," + "choices: " + "[a]:" + a + "," + "[b]:" + b + "," + "[c]:" + c + "," + "[d]:" + d);
                    localQuestionAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
