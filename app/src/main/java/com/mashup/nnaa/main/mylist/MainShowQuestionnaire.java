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
import com.mashup.nnaa.network.model.QuestionnaireAnswerDto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainShowQuestionnaire extends AppCompatActivity {

    private Button btnComplete;
    private TextView txtCategory, txtAnswer, txtQuestion, txtType, txtBookmark, showContent, showType, showChoice, showAnswer;
    private ImageView showClose;
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

        showClose.setOnClickListener(view -> finish());
        btnComplete.setOnClickListener(view -> finish());

        String category = intent.getStringExtra("category");
        String answer = intent.getStringExtra("answer");
        String question = intent.getStringExtra("questions");

        try {
            JSONObject object = new JSONObject(Objects.requireNonNull(question));

            for (int i = 0; i < object.length(); i++) {
                JSONObject jsonObject = object.getJSONObject("additionalProp1");
                String content = jsonObject.getString("content");
                String type = jsonObject.getString("type");
                boolean bookmark = jsonObject.getBoolean("isBookmarked");

                if (jsonObject.getJSONObject("choices").isNull("choices")) {
                    String choice = jsonObject.getString("choices");
                    JSONObject c_object = new JSONObject(choice);
                    a = c_object.getString("a");
                    b = c_object.getString("b");
                    c = c_object.getString("c");
                    d = c_object.getString("d");

                    showChoice.setText(String.format("a:%s,b:%s,c:%s,d:%s", a, b, c, d));
                }

                showContent.setText(content);
                showType.setText(type);
                showAnswer.setText(answer);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
