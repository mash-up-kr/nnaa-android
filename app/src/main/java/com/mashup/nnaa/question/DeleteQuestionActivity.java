package com.mashup.nnaa.question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.DeleteQuestionItem;
import com.mashup.nnaa.data.QuestionItem;
import com.mashup.nnaa.util.DeleteAdapter;
import com.mashup.nnaa.util.ItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteQuestionActivity extends AppCompatActivity {

    private DeleteAdapter deleteAdapter;
    private ItemTouchHelper helper;
    private TextView txt_delete_name;
    private Button btn_delete, btn_delete_cancel;
    private ArrayList<QuestionItem> dArrayList;

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

        RecyclerView recyclerDelete = findViewById(R.id.recycler_delete);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerDelete.setLayoutManager(linearLayoutManager);

        Intent question_intent = getIntent();

        ArrayList<QuestionItem> list = (ArrayList<QuestionItem>) question_intent.getSerializableExtra("list");
        dArrayList = list;
        deleteAdapter = new DeleteAdapter(this,list);
        recyclerDelete.setAdapter(deleteAdapter);

        helper = new ItemTouchHelper(new ItemTouchHelperCallback(deleteAdapter));
        helper.attachToRecyclerView(recyclerDelete);

        deleteAdapter.notifyDataSetChanged();
    }
}
