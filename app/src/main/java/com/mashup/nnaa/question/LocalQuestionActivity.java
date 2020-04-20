package com.mashup.nnaa.question;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.Choices;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.LocalQuestionAdapter;
import com.mashup.nnaa.util.SharedPrefHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocalQuestionActivity extends AppCompatActivity {

    private EditText makeQuestion;
    private ImageView question_delete;
    private LocalQuestionAdapter localQuestionAdapter;
    private List<NewQuestionDto> localList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_question);

        Intent intent = getIntent();
        String name_type = intent.getStringExtra("type");
        String name = AccountManager.getInstance().getUserAuthHeaderInfo().getName();

        makeQuestion = findViewById(R.id.local_mk_edit);
        question_delete = findViewById(R.id.img_delete);

        RecyclerView local_recycler = findViewById(R.id.local_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        local_recycler.setLayoutManager(linearLayoutManager);
        local_recycler.setHasFixedSize(true);

        localList = new ArrayList<>();
        localQuestionAdapter = new LocalQuestionAdapter(this, localList);
        local_recycler.setAdapter(localQuestionAdapter);

        makeQuestion.setFocusable(false);
        makeQuestion.setOnClickListener(view -> {
            Intent make = new Intent(view.getContext(), MakeQuestionActivity.class);
            startActivityForResult(make, 0);
        });

        question_delete.setOnClickListener(view -> {
            Toast.makeText(LocalQuestionActivity.this, "질문삭제 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent deleteintent = new Intent(view.getContext(), DeleteQuestionActivity.class);
            deleteintent.putExtra("name", name);
            deleteintent.putExtra("name_type",name_type);

            startActivity(deleteintent);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case MakeQuestionActivity.RESULT_UPDATE_OK:

                Intent intent1 = getIntent();
                String category = intent1.getStringExtra("category");

                String contents = data.getStringExtra("content");
                String type = data.getStringExtra("type");
                String a = data.getStringExtra("setA");
                String b = data.getStringExtra("setB");
                String c = data.getStringExtra("setC");
                String d = data.getStringExtra("setD");

                Choices choices = new Choices();
                choices.setA(a);
                choices.setB(b);
                choices.setC(c);
                choices.setD(d);


                if (contents != null && !contents.isEmpty()) {

                    NewQuestionDto newQuestionDto = new NewQuestionDto("", contents, category, type, choices);
                    localList.add(newQuestionDto);
                    Log.v("데이터 추가", "질문생성: " + "content: " + contents + "," + "category: " + category + "," +
                            "type: " + type + "," + "choices: " + "[a]:" + a + "," + "[b]:" + b + "," + "[c]:" + c + "," + "[d]:" + d);
                    localQuestionAdapter.notifyDataSetChanged();
                }
                break;
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
