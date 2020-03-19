package com.mashup.nnaa.question;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.FavoritesItem;
import com.mashup.nnaa.util.FavoritesAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private FavoritesAdapter adapter3;
    private ArrayList<FavoritesItem> fList;
    Button btn_favorites;
    ImageButton imgbtn_past, imgbtn_cancel;
    EditText edit_custom;
    ImageView img_favorites, img_recycler;
    TextView txt_favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        btn_favorites = findViewById(R.id.btn_favorites);
        imgbtn_past = findViewById(R.id.imgbtn_past);
        imgbtn_cancel = findViewById(R.id.imgbtn_cancel);
        edit_custom = findViewById(R.id.edit_custom);
        img_favorites = findViewById(R.id.img_favorites);
        txt_favorites = findViewById(R.id.txt_favorites);
        img_recycler = findViewById(R.id.img_recycler);

        imgbtn_past.setOnClickListener(view -> {
            finish();
        });


        init();

        getItem();


        imgbtn_cancel.setOnClickListener(view -> {
            finish();
        });

        init();

        getItem();

        edit_custom.setOnClickListener(view -> {
            Intent edit_intent = new Intent(FavoritesActivity.this, CustomQuestionActivity.class);
            startActivity(edit_intent);
        });

    }

    private void init() {

        RecyclerView favorites_recycler = findViewById(R.id.favorites_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        favorites_recycler.setLayoutManager(linearLayoutManager);

        adapter3 = new FavoritesAdapter();
        favorites_recycler.setAdapter(adapter3);

    }

    private void getItem() {

        List<String> listQuestion = Arrays.asList("로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?",
                "로또에 당첨된다면 하고 싶은 일이 있나요?");


        for (int i = 0; i < listQuestion.size(); i++) {

            FavoritesItem fitem = new FavoritesItem();
            fitem.setFavoritesQuestion(listQuestion.get(i));

            adapter3.addItem(fitem);
        }
        adapter3.notifyDataSetChanged();
    }
}
