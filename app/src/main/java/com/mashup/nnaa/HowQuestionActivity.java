package com.mashup.nnaa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.mashup.nnaa.data.HowQuestionItem;
import com.mashup.nnaa.util.HowQuestionAdapter;

import java.util.ArrayList;

import static com.mashup.nnaa.util.HowQuestionAdapter.FIRST_CONTENT;
import static com.mashup.nnaa.util.HowQuestionAdapter.SECOND_CONTENT;
import static com.mashup.nnaa.util.HowQuestionAdapter.THIRD_CONTENT;

public class HowQuestionActivity extends AppCompatActivity {


    private HowQuestionAdapter adapter2;
    private ArrayList<HowQuestionItem> mList;

    //   ArrayList<HowQuestionItem> mList = new ArrayList<HowQuestionItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_question);

        Intent intent = getIntent();

        this.initializeData();

        RecyclerView recyclerView = findViewById(R.id.recycler2);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new HowQuestionAdapter(mList));


    }

    public void initializeData() {

        mList = new ArrayList<>();

        //여기서 int 값을 뭐를 주던 뷰타입에따라 화면이 보여짐(null 제외)
        mList.add(new HowQuestionItem("객관식", R.id.img_how_item, FIRST_CONTENT));

        mList.add(new HowQuestionItem("주관식",R.id.img_long, SECOND_CONTENT));


        mList.add(new HowQuestionItem("O/X", R.id.img_good, THIRD_CONTENT));


    }


}


