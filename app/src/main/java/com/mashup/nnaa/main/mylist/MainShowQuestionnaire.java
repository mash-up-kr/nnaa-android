package com.mashup.nnaa.main.mylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.Questionnaire;
import com.mashup.nnaa.network.model.QuestionnaireAnswerDto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainShowQuestionnaire extends AppCompatActivity {

    private Button btnComplete;
    private TextView showContent, showType, showChoice, showAnswer, showAll;
    private ImageView showClose;
    ArrayList<String> contentarray = new ArrayList<>();
    ArrayList<String> typearray = new ArrayList<>();
    ArrayList<String> choicearray = new ArrayList<>();
    ArrayList<String> allarray = new ArrayList<>();
    String a = "";
    String b = "";
    String c = "";
    String d = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_show_questionnaire);

        Intent intent = getIntent();

        btnComplete = findViewById(R.id.btn_complete);
        showClose = findViewById(R.id.show_close);
        showContent = findViewById(R.id.custom_first_edit);
        showType = findViewById(R.id.show_type_txt);
        showChoice = findViewById(R.id.custom_choice_edit1);
        showAnswer = findViewById(R.id.show_answer_text);
        showAll = findViewById(R.id.show_all_txt);

        showClose.setOnClickListener(view -> finish());
        btnComplete.setOnClickListener(view -> finish());

        String answer = intent.getStringExtra("answer");
        String question = intent.getStringExtra("questions");
        String test = intent.getStringExtra("test");

        ArrayList<String> key = new ArrayList<>();
        ArrayList<String> val = new ArrayList<>();
        ArrayList<String> answerkey = new ArrayList<>();
        ArrayList<String> answerval = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(Objects.requireNonNull(test));
            JSONObject qobject = new JSONObject(Objects.requireNonNull(question));
            JSONObject answerObject = new JSONObject(Objects.requireNonNull(answer));

            String Answer = answerObject.getString("answers");

            String value = object.getString("questions");
            JSONObject object1 = new JSONObject(value);
            Iterator j = object1.keys();

            while (j.hasNext()) {
                String b = j.next().toString();
                key.add(b);
            }
            JSONObject object2 = new JSONObject(Answer);
            Iterator iterator = object2.keys();

            while (iterator.hasNext()) {
                String a = iterator.next().toString();
                answerkey.add(a);
            }

            for (int k = 0; k < key.size(); k++) {
                val.add(object1.getString(key.get(k)));
                answerval.add(object2.getString(answerkey.get(k)));
            }

            for (int i = 0; i < qobject.length(); i++) {
                JSONObject jsonObject = qobject.getJSONObject(key.get(i));

                String content = jsonObject.getString("content");
                contentarray.add(i + 1 + ":" + content);
                String type = jsonObject.getString("type");
                typearray.add(i + 1 + ":" + type);

                String choice = jsonObject.getString("choices");

                JSONObject c_object = new JSONObject(choice);
                if (c_object.has("a") && c_object.has("b") && c_object.has("c") && c_object.has("d")) {
                    a = c_object.getString("a");
                    b = c_object.getString("b");
                    c = c_object.getString("c");
                    d = c_object.getString("d");
                    choicearray.add("a:" + a);
                    choicearray.add("b:" + b);
                    choicearray.add("c:" + c);
                    choicearray.add("d:" + d);
                }
                allarray.add(i + 1 + "번 " + "질문지 유형: " + type);
                allarray.add(i + 1 + "번 " + "질문지 내용: " + content);
                allarray.add(i + 1 + "번 " + "객관식 보기: " + choice);
            }
            showChoice.setText(choicearray.toString());
            showContent.setText(contentarray.toString());
            showType.setText(typearray.toString());
            showAnswer.setText(String.format("답변: %s", answerval.toString()));
            showAll.setText(allarray.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
