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
import com.mashup.nnaa.MakeQuestionActivity;
import com.mashup.nnaa.data.QuestionItem;
import com.mashup.nnaa.util.FavoritesAdapter;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private FavoritesAdapter favoritesAdapter;
    private ArrayList<QuestionItem> fList;
    Button btn_favorites;
    ImageButton imgbtn_past, imgbtn_cancel;
    EditText edit_custom;
    ImageView img_favorites, img_recycler;
    TextView txt_favorites, getType;

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
        getType = findViewById(R.id.get_type);

        Intent typeintent = getIntent();
        if (typeintent != null && typeintent.getExtras() != null) {
            String type = typeintent.getStringExtra("type");
            getType.setText(type);
        }

        imgbtn_past.setOnClickListener(view -> {
            finish();
        });

        imgbtn_cancel.setOnClickListener(view -> {
            finish();
        });


        edit_custom.setOnClickListener(view -> {
            Intent edit_intent = new Intent(FavoritesActivity.this, MakeQuestionActivity.class);
            edit_intent.putExtra("type", String.valueOf(getType));
            startActivity(edit_intent);
        });


        RecyclerView favorites_recycler = findViewById(R.id.favorites_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        favorites_recycler.setLayoutManager(linearLayoutManager);


        Intent intent = getIntent();
        ArrayList<QuestionItem> list = (ArrayList<QuestionItem>) intent.getSerializableExtra("flist");

        fList = list;
        favoritesAdapter = new FavoritesAdapter(this, list);
        favorites_recycler.setAdapter(favoritesAdapter);
        favoritesAdapter.notifyDataSetChanged();

    }
}
