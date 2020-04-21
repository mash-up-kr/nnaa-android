package com.mashup.nnaa.question;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.mashup.nnaa.R;
import com.mashup.nnaa.data.Choices;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.LocalQuestionAdapter;
import com.mashup.nnaa.util.SharedPrefHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.converter.gson.GsonConverterFactory;

public class LocalQuestionActivity extends AppCompatActivity {

    private EditText makeQuestion;
    private ImageView question_delete;
    private ImageButton img_past;
    private Button btn_favor;
    private LocalQuestionAdapter localQuestionAdapter;
    private ArrayList<NewQuestionDto> localList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_question);

        Intent intent = getIntent();
        String name_type = intent.getStringExtra("type");
        String name = AccountManager.getInstance().getUserAuthHeaderInfo().getName();

        img_past = findViewById(R.id.imgbtn_past);
        makeQuestion = findViewById(R.id.local_mk_edit);
        question_delete = findViewById(R.id.img_delete);
        btn_favor = findViewById(R.id.btn_favorites);

        img_past.setOnClickListener(view -> {
            finish();
        });

        RecyclerView local_recycler = findViewById(R.id.local_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        local_recycler.setLayoutManager(linearLayoutManager);
        local_recycler.setHasFixedSize(true);

        localList = new ArrayList<>();
        localQuestionAdapter = new LocalQuestionAdapter(this, localList);
        local_recycler.setAdapter(localQuestionAdapter);

        for (NewQuestionDto newQuestionDto : getQuestion()) {
            localList.add(newQuestionDto);
            localQuestionAdapter.notifyDataSetChanged();
        }

        makeQuestion.setFocusable(false);
        makeQuestion.setOnClickListener(view -> {
            Intent make = new Intent(view.getContext(), MakeQuestionActivity.class);
            startActivityForResult(make, 0);
        });

        // 질문삭제 페이지
        question_delete.setOnClickListener(view -> {
            Toast.makeText(LocalQuestionActivity.this, "질문삭제 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent deleteintent = new Intent(view.getContext(), DeleteQuestionActivity.class);
            deleteintent.putExtra("name", name);
            deleteintent.putExtra("name_type", name_type);
            deleteintent.putExtra("list", localList);

            startActivityForResult(deleteintent, 1000);
        });
        btn_favor.setOnClickListener(view ->
        {
            Intent fav = new Intent(LocalQuestionActivity.this, FavoritesActivity.class);
            startActivity(fav);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case MakeQuestionActivity.RESULT_UPDATE_OK:

                Intent intent1 = getIntent();
                String category = intent1.getStringExtra("category");

                String contents = data.getStringExtra("content");
                String type = data.getStringExtra("type");
                String a = data.getStringExtra("setA");
                String b = data.getStringExtra("setB");
                String c = data.getStringExtra("setC");
                String d = data.getStringExtra("setD");

                Choices choices = new Choices();
                choices.setA(a);
                choices.setB(b);
                choices.setC(c);
                choices.setD(d);

                if (contents != null && !contents.isEmpty()) {
                    NewQuestionDto newQuestionDto = new NewQuestionDto("", contents, category, type, choices);

                    localList.add(newQuestionDto);
                    setQuestion(localList);

                    Log.v("Question Add", "질문생성: " + "content: " + contents + "," + "category: " + category + "," +
                            "type: " + type + "," + "choices: " + "[a]:" + a + "," + "[b]:" + b + "," + "[c]:" + c + "," + "[d]:" + d);

                    localQuestionAdapter.notifyDataSetChanged();
                }
                break;

            case DeleteQuestionActivity.RESULT_DELETE_OK:

                localList = (ArrayList<NewQuestionDto>) data.getSerializableExtra("delete");
                setQuestion(localList);
                localQuestionAdapter.setLocalQuestionList(localList);

                break;
        }
    }


    private void setQuestion(ArrayList<NewQuestionDto> localList) {
        SharedPreferences prefs = getSharedPreferences("list", 0);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(localList);
        editor.putString("question", json);
        editor.apply();
    }

    private ArrayList<NewQuestionDto> getQuestion() {
        SharedPreferences prefs = getSharedPreferences("list", 0);
        Gson gson = new Gson();
        String json = prefs.getString("question", "");
        Type type = new TypeToken<ArrayList<NewQuestionDto>>() {
        }.getType();
        ArrayList<NewQuestionDto> list = gson.fromJson(json, type);
        return list;
    }
}
