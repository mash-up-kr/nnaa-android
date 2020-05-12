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
    private TextView txtCategory, txtAnswer, txtQuestion, txtType, txtBookmark, showContent, showType, showChoice, showAnswer, showAll;
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
        showAll = findViewById(R.id.show_all_txt);

        showClose.setOnClickListener(view -> finish());
        btnComplete.setOnClickListener(view -> finish());

        String category = intent.getStringExtra("category");
        String answer = intent.getStringExtra("answer");
        String question = intent.getStringExtra("questions");
        String test = intent.getStringExtra("test");

        ArrayList<String> key = new ArrayList<>();
        ArrayList<String> val = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        JSONObject allshow = new JSONObject();
        try {
            JSONObject object = new JSONObject(Objects.requireNonNull(test));
            JSONObject qobject = new JSONObject(Objects.requireNonNull(question));

            String value = object.getString("questions");
            Log.v("@@@@", test);

            JSONObject object1 = new JSONObject(value);

            Iterator j = object1.keys();
            while (j.hasNext()) {
                String b = j.next().toString();
                key.add(b);

            }
            for (int k = 0; k < key.size(); k++) {
                val.add(object1.getString(key.get(k)));
                hashMap.put(key.get(k), val.get(k));
            }
            JSONObject object2 = new JSONObject(hashMap);

            JSONObject all = new JSONObject();

            for (int i = 0; i < qobject.length(); i++) {
                JSONObject jsonObject = qobject.getJSONObject("additionalProp1");

                String content = jsonObject.getString("content");
                String type = jsonObject.getString("type");
                boolean bookmark = jsonObject.getBoolean("isBookmarked");

                all.put("type",type);
                all.put("content",content);
                if (jsonObject.getJSONObject("choices").isNull("choices")) {
                    String choice = jsonObject.getString("choices");

                    JSONObject c_object = new JSONObject(choice);
                    a = c_object.getString("a");
                    b = c_object.getString("b");
                    c = c_object.getString("c");
                    d = c_object.getString("d");

                    all.put("choices",choice);

                    showChoice.setText(String.format("a:%s,b:%s,c:%s,d:%s", a, b, c, d));

                    if (Objects.equals(choice, "null")) {
                       showChoice.setText("보기가 없습니다.");
                    }
                }
                showContent.setText(content);
                showType.setText(type);

                if (answer == null) {
                    showAnswer.setText("답변 없음");
                } else {
                    showAnswer.setText(answer);
                }
                Set set = hashMap.entrySet();
                for (Object o : set) {
                    Map.Entry entry = (Map.Entry) o;
                    String df = (String) entry.getKey();
                    String ff = (String) entry.getValue();

                    allshow.put(df,all);
                }
            }


            showAll.setText(hashMap.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
