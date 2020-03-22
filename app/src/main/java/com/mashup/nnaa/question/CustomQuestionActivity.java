package com.mashup.nnaa.question;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mashup.nnaa.R;

import com.mashup.nnaa.data.QuestionItem;
import com.mashup.nnaa.util.CustomQuestionAdapter;

import java.util.ArrayList;

import static com.mashup.nnaa.util.CustomQuestionAdapter.FIRST_CONTENT;
import static com.mashup.nnaa.util.CustomQuestionAdapter.FORTH_CONTENT;
import static com.mashup.nnaa.util.CustomQuestionAdapter.SECOND_CONTENT;
import static com.mashup.nnaa.util.CustomQuestionAdapter.THIRD_CONTENT;

public class CustomQuestionActivity extends AppCompatActivity {

    ImageButton imgbtn_past, imgbtn_cancel;
    Button btn_done;
    ImageView img_recycler;
    private CustomQuestionAdapter customQuestionAdapter;
    private ArrayList<QuestionItem> cArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_question);

        Intent intent = getIntent();

        imgbtn_past = findViewById(R.id.imgbtn_past);
        imgbtn_cancel = findViewById(R.id.imgbtn_cancel);
        btn_done = findViewById(R.id.btn_done);
        img_recycler = findViewById(R.id.img_recycler);

        imgbtn_past.setOnClickListener(view -> {
            Intent past_intent = new Intent(CustomQuestionActivity.this, FavoritesActivity.class);
            startActivity(past_intent);
        });

        imgbtn_cancel.setOnClickListener(view -> {
            Intent cancel_intent = new Intent(CustomQuestionActivity.this, FavoritesActivity.class);
            startActivity(cancel_intent);
        });

        // test
        btn_done.setOnClickListener(view -> {
            btn_done.setBackgroundColor(getResources().getColor(R.color.colorBlue));

        });
        this.initializeData();
    }

    private void initializeData() {

        RecyclerView recyclerCustom = findViewById(R.id.custom_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerCustom.setLayoutManager(linearLayoutManager);
        cArrayList = new ArrayList<>();
        customQuestionAdapter = new CustomQuestionAdapter(this, cArrayList);
        recyclerCustom.setAdapter(customQuestionAdapter);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        cArrayList.add(new QuestionItem("첫번쨰, 원하는 질문을 입력해주세요", "", R.id.img_add, FIRST_CONTENT));
        cArrayList.add(new QuestionItem("두번째, 문제 형식을 설정해주세요", "", R.id.img_how_item, SECOND_CONTENT));
        cArrayList.add(new QuestionItem("세번째, 보기를 설정해주세요", "", R.id.img_add, THIRD_CONTENT));
        cArrayList.add(new QuestionItem("짝짝짝 질문 생성 완료!", "", R.id.img_bad, FORTH_CONTENT));
    }
}
