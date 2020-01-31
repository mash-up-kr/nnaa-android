package com.mashup.nnaa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mashup.nnaa.data.CustomQuestionItem;
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
    //  private CustomQuestionAdapter adapter;
    private ArrayList<CustomQuestionItem> cList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_question);

        Intent intent = getIntent();

        imgbtn_past = findViewById(R.id.imgbtn_past);
        imgbtn_cancel = findViewById(R.id.imgbtn_cancel);
        btn_done = findViewById(R.id.btn_done);
        img_recycler = findViewById(R.id.img_recycler);

        imgbtn_past.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent past_intent = new Intent(CustomQuestionActivity.this, FavoritesActivity.class);
                startActivity(past_intent);
            }
        });

        imgbtn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent cancel_intent = new Intent(CustomQuestionActivity.this, FavoritesActivity.class);
                startActivity(cancel_intent);
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_done.setBackgroundColor(getResources().getColor(R.color.colorBlue));
            }
        });

        this.initializeData();

        RecyclerView recyclerView = findViewById(R.id.custom_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new CustomQuestionAdapter(cList));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
    private void initializeData() {

        cList = new ArrayList<>();

        cList.add(new CustomQuestionItem("첫번쨰, 원하는 질문을 입력해주세요", "", R.id.img_add, FIRST_CONTENT));
        cList.add(new CustomQuestionItem("두번째, 문제 형식을 설정해주세요","",R.id.img_how_item,SECOND_CONTENT));
        cList.add(new CustomQuestionItem("세번째, 보기를 설정해주세요","",R.id.img_add,THIRD_CONTENT));
        cList.add(new CustomQuestionItem("짝짝짝 질문 생성 완료!","",R.id.img_bad, FORTH_CONTENT));
    }
}
