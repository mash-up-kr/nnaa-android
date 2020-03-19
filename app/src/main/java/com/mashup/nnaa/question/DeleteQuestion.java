package com.mashup.nnaa.question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.DeleteQuestionItem;
import com.mashup.nnaa.util.DeleteAdapter;

import java.util.Arrays;
import java.util.List;

public class DeleteQuestionActivity extends AppCompatActivity {

    DeleteAdapter deleteAdapter;
    TextView txt_delete_name;
    Button btn_delete, btn_delete_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_question);

        btn_delete = findViewById(R.id.btn_delete);
        btn_delete_cancel = findViewById(R.id.btn_delete_cancel);
        txt_delete_name = findViewById(R.id.txt_delete_name);

        btn_delete_cancel.setOnClickListener(view -> {
            Intent intent = new Intent(DeleteQuestionActivity.this, QuestionActivity.class);
            startActivity(intent);
        });

        Intent intent = getIntent();

        // QuestionActivity 에서 type, name 받아오자
        if (intent != null && intent.getExtras() != null) {
            String name = intent.getStringExtra("name");

            txt_delete_name.setText(name);
        }

        init();

        getData();

    }

    private void init() {

        RecyclerView recyclerDelete = findViewById(R.id.recycler_delete);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerDelete.setLayoutManager(linearLayoutManager);

        deleteAdapter = new DeleteAdapter();
        recyclerDelete.setAdapter(deleteAdapter);
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
