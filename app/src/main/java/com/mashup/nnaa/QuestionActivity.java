package com.mashup.nnaa;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.data.QuestionItem;
import com.mashup.nnaa.util.QuestionAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class QuestionActivity extends AppCompatActivity {

    ImageView img_delete, img_add;
    TextView txt_name;
    Button btn_cancel, btn_next;

    private QuestionAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();

        txt_name = findViewById(R.id.txt_name);

        // setTypeActivity에서 타입, 이름 받아오자
        if (intent != null) {
            if (intent.getExtras() != null) {
                String name = intent.getStringExtra("name");
                String type = intent.getStringExtra("typename");

                txt_name.setText(String.format("%s인 , ", type));
                txt_name.append(name + "님 께");
            }
        }

        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(view -> {
            // 보낸 질문함으로 넘어감
        });
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(view -> {
            Intent cancel_intent = new Intent(getApplicationContext(), SetTypeOfFriendActivity.class);
            startActivity(cancel_intent);
        });

        img_delete = findViewById(R.id.img_delete);

        String name = txt_name.getText().toString();

        img_delete.setOnClickListener(view -> {
            Toast.makeText(QuestionActivity.this, "질문삭제 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent deleteintent = new Intent(getApplicationContext(), DeleteQuestion.class);
            deleteintent.putExtra("name", name);
            startActivity(deleteintent);
        });

        img_add = findViewById(R.id.img_add);

        img_add.setOnClickListener(view -> {
            Toast.makeText(QuestionActivity.this, "즐겨찾기 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent bookmarkintent = new Intent(QuestionActivity.this, FavoritesActivity.class);
            startActivity(bookmarkintent);
        });

        init();

        getItem();
    }

    private void init() {

        RecyclerView recyclerQuestion = findViewById(R.id.recycler_question);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerQuestion.setLayoutManager(linearLayoutManager);

        adapter = new QuestionAdapter();
        recyclerQuestion.setAdapter(adapter);

    }

    private void getItem() {

        List<String> listInitial = Arrays.asList("Q.", "Q.", "Q.", "Q.", "Q.", "Q.", "Q.",
                "Q.", "Q.", "Q.", "Q.", "Q.", "Q.", "Q.");

        List<String> listContent = Arrays.asList("엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?",
                "엄마가 가장 좋아하는 음식은?");

        for (int i = 0; i < listInitial.size(); i++) {

            QuestionItem aitem = new QuestionItem();
            aitem.setMainQ(listInitial.get(i));
            aitem.setQuestionary(listContent.get(i));

            adapter.addItem(aitem);
        }
        adapter.notifyDataSetChanged();
    }
}
