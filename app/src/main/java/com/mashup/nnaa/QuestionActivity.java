package com.mashup.nnaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.data.QuestionItem;
import com.mashup.nnaa.util.QuestionAdapter;

import java.util.Arrays;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    ImageView img_delete, img_add;
    Button btn_cancel, btn_next;

    private QuestionAdapter adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();

        btn_next = findViewById(R.id.btn_next);
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancel_intent = new Intent(QuestionActivity.this, SetTypeOfFriendActivity.class);
                startActivity(cancel_intent);
            }
        });
        img_delete = findViewById(R.id.img_delete);

        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuestionActivity.this, "질문을 삭제하러 갈게용", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuestionActivity.this, DeleteQuestion.class);
                startActivity(intent);
            }
        });

        img_add = findViewById(R.id.img_add);

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuestionActivity.this, "다음 화면으로 넘어갈게", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuestionActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });


        init();

        getItem();
    }

    private void init() {

        RecyclerView recycler1 = findViewById(R.id.recycler1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler1.setLayoutManager(linearLayoutManager);

        adapter = new QuestionAdapter();
        recycler1.setAdapter(adapter);

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
