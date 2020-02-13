package com.mashup.nnaa;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.data.DeleteQuestionItem;
import com.mashup.nnaa.util.DeleteAdapter;

import java.util.Arrays;
import java.util.List;

public class DeleteQuestion extends AppCompatActivity {


    DeleteAdapter deleteAdapter;

    Button btn_delete, btn_delete_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_question);

        Intent intent = getIntent();

        init();

        getData();

        btn_delete = findViewById(R.id.btn_delete);
        btn_delete_cancel = findViewById(R.id.btn_delete_cancel);



        btn_delete_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteQuestion.this, QuestionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {

        RecyclerView recyclerView = findViewById(R.id.recycler3);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        deleteAdapter = new DeleteAdapter();
        recyclerView.setAdapter(deleteAdapter);
    }

    private void getData() {

        List<String> list = Arrays.asList("엄마가 가장 좋아하는 음식은?",
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

        for (int i = 0; i < list.size(); i++) {

            DeleteQuestionItem deleteitem = new DeleteQuestionItem();

            deleteitem.setQuestion_Txt(list.get(i));

            deleteAdapter.addItem(deleteitem);
        }

        deleteAdapter.notifyDataSetChanged();
    }

}
