package com.mashup.nnaa.question;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.CustomQuestionItem;
import com.mashup.nnaa.data.QuestionItem;
import com.mashup.nnaa.network.QuestionControllerService;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.Question;
import com.mashup.nnaa.select.SetTypeOfFriendActivity;
import com.mashup.nnaa.reply.ReplyActivity;
import com.mashup.nnaa.util.QuestionAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {

    private ImageView img_delete, img_add;
    private TextView txt_name, txt_type;
    private Button btn_cancel, btn_next;

    private QuestionAdapter questionAdapter;
    private ArrayList<QuestionItem> questionList;

    private QuestionControllerService questionControllerService = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();

        txt_name = findViewById(R.id.txt_name);
        txt_type = findViewById(R.id.txt_type);
        btn_next = findViewById(R.id.btn_next);
        btn_cancel = findViewById(R.id.btn_cancel);
        img_delete = findViewById(R.id.img_delete);
        img_add = findViewById(R.id.img_add);

        // setTypeActivity에서 타입, 이름 받아오자
        if (intent != null && intent.getExtras() != null) {
            String name = intent.getStringExtra("name");
            String type = intent.getStringExtra("typename");

            txt_type.setText(String.format("%s인 , ", type));
            txt_name.setText(String.format("%s께", name));
        }

        btn_next.setOnClickListener(view -> {
            // 보낸 질문함으로 넘어감
            String replyname = txt_name.getText().toString();
            Intent reply_intent = new Intent(QuestionActivity.this, ReplyActivity.class);
            reply_intent.putExtra("reply name", replyname);
            startActivity(reply_intent);

        });


        btn_cancel.setOnClickListener(view -> {
            Intent cancel_intent = new Intent(getApplicationContext(), SetTypeOfFriendActivity.class);
            startActivity(cancel_intent);
        });

        Intent test = getIntent();

        RecyclerView recyclerQuestion = findViewById(R.id.recycler_question);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerQuestion.setLayoutManager(linearLayoutManager);

        //ArrayList<QuestionItem> list = (ArrayList<QuestionItem>) test.getSerializableExtra("test");

        ArrayList<QuestionItem> list = new ArrayList<>();
        list.add(new QuestionItem("Q.", "dd",0,0));
        list.add(new QuestionItem("Q.", "dd",0,0));
        list.add(new QuestionItem("Q.", "dd",0,0));
        list.add(new QuestionItem("Q.", "dd",0,0));
        list.add(new QuestionItem("Q.", "dd",0,0));
        list.add(new QuestionItem("Q.", "dd",0,0));
        list.add(new QuestionItem("Q.", "dd",0,0));
        list.add(new QuestionItem("Q.", "dd",0,0));
        list.add(new QuestionItem("Q.", "dd",0,0));

        questionList = list;
        questionAdapter = new QuestionAdapter(this, list);
        recyclerQuestion.setAdapter(questionAdapter);

        questionAdapter.notifyDataSetChanged();

        String name = txt_name.getText().toString();
        String type = txt_type.getText().toString();
        img_delete.setOnClickListener(view -> {
            Toast.makeText(QuestionActivity.this, "질문삭제 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent deleteintent = new Intent(getApplicationContext(), DeleteQuestionActivity.class);
            deleteintent.putExtra("name", name);
            deleteintent.putExtra("type",type);
            deleteintent.putExtra("list", list);
            startActivity(deleteintent);
        });

        img_add.setOnClickListener(view -> {
            Toast.makeText(QuestionActivity.this, "즐겨찾기 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent bookmarkintent = new Intent(QuestionActivity.this, FavoritesActivity.class);
            bookmarkintent.putExtra("flist", list);
            startActivity(bookmarkintent);
        });


//        questionControllerService = RetrofitHelper.getInstance().getQuestion(new Callback<List<Question>>() {
//            @Override
//            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
//                questionList = response.body();
//                if(questionList!=null) {
//                    Log.v("QuestionRandom", "성공:" + "code : " + response.code());
//                    questionAdapter.setQuestionList(questionList);
//                }
//                else {
//                    Log.v("QuestionRandom", "No Question");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Question>> call, Throwable t) {
//                Log.v("QuestionRandom", "에러:" + t.getMessage());
//            }
//        });


    }
}
