package com.mashup.nnaa.question;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashup.nnaa.R;
import com.mashup.nnaa.data.Choices;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.QuestionAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {

    private ImageView img_add, img_delete;
    private TextView txt_name, txt_type;
    private EditText makeQuestion;
    private Button btn_next;
    private ImageView btn_cancel;
    private QuestionAdapter questionAdapter;
    private ArrayList<NewQuestionDto> questionList;
    private String TAG = "QuestionActivity";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();

        String category = intent.getStringExtra("category");
        String type = intent.getStringExtra("type");
        String name = intent.getStringExtra("name");

        txt_name = findViewById(R.id.txt_name);
        txt_type = findViewById(R.id.txt_type);
        btn_next = findViewById(R.id.btn_next);
        btn_cancel = findViewById(R.id.btn_cancel);
        img_add = findViewById(R.id.img_add);
        img_delete = findViewById(R.id.img_delete);
        makeQuestion = findViewById(R.id.edit_make);

        txt_type.setText(String.format("%s인 , ", type));
        txt_name.setText(String.format("%s께", name));

        makeQuestion.setOnClickListener(view -> {
            Toast.makeText(QuestionActivity.this, "질문생성 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent bookmarkintent = new Intent(QuestionActivity.this, MakeQuestionActivity.class);
            startActivityForResult(bookmarkintent, 0);
        });

        btn_next.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("질문지 보내기");
            builder.setMessage("질문지를 보낼까요?");
            builder.setPositiveButton("확인", (dialogInterface, i) -> {
                        try {
                            JSONArray jsonArray = new JSONArray();

                            for (int j = 0; j < questionList.size(); j++) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("id", questionList.get(j).getId());
                                jsonObject.put("type", questionList.get(j).getType());
                                jsonObject.put("content", questionList.get(j).getContent());

                                if (questionList.get(j).getChoices() != null) {
                                    JSONObject object = new JSONObject();
                                    object.put("a", questionList.get(j).getChoices().getA());
                                    object.put("b", questionList.get(j).getChoices().getB());
                                    object.put("c", questionList.get(j).getChoices().getC());
                                    object.put("d", questionList.get(j).getChoices().getD());
                                    jsonObject.put("choices", object);
                                } else jsonObject.put("choices", "null");

                                jsonObject.put("isBookmarked", questionList.get(j).isBookmarked());
                                jsonArray.put(jsonObject);

                            }

                            Toast.makeText(getApplicationContext(), "질문지를 보내겠습니다.", Toast.LENGTH_SHORT).show();
                            Intent reply_intent = new Intent(QuestionActivity.this, SharingActivity.class);
                            reply_intent.putExtra("category", category);
                            reply_intent.putExtra("list", jsonArray.toString());
                            startActivity(reply_intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
            );
            builder.setNegativeButton("취소", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "질문지 보내기 취소", Toast.LENGTH_SHORT).show());
            builder.show();
        });

        btn_cancel.setOnClickListener(view -> finish());


        RecyclerView recyclerQuestion = findViewById(R.id.recycler_question);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerQuestion.setLayoutManager(linearLayoutManager);
        recyclerQuestion.setHasFixedSize(true);

        questionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(this, questionList);
        recyclerQuestion.setAdapter(questionAdapter);


        this.getQuestionRandom();

        img_add.setOnClickListener(view -> {
            Toast.makeText(QuestionActivity.this, "즐겨찾기 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent bookmarkintent = new Intent(QuestionActivity.this, FavoritesActivity.class);
            startActivity(bookmarkintent);
        });

        img_delete.setOnClickListener(view -> {
            Toast.makeText(QuestionActivity.this, "질문삭제 페이지로 넘어가겠습니다!", Toast.LENGTH_SHORT).show();
            Intent deleteintent = new Intent(view.getContext(), DeleteQuestionActivity.class);
            deleteintent.putExtra("name", name);
            deleteintent.putExtra("type", type);
            deleteintent.putExtra("list", questionList);

            startActivityForResult(deleteintent, 1000);
        });
    }

    private void getQuestionRandom() {
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
        String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();

        Intent intent1 = new Intent(QuestionActivity.this, QuestionAdapter.class);
        intent1.putExtra("category", category);

        RetrofitHelper.getInstance().getQuestion(id, token, category, "10", new Callback<ArrayList<NewQuestionDto>>() {
            @Override
            public void onResponse(Call<ArrayList<NewQuestionDto>> call, Response<ArrayList<NewQuestionDto>> response) {
                if (questionList != null && response.body() != null) {
                    questionList = response.body();

                    questionAdapter.setQuestionList(questionList);

                    if (getQuestion() != null) {
                        for (NewQuestionDto newQuestionDto : getQuestion()) {
                            questionList.add(newQuestionDto);
                            questionAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NewQuestionDto>> call, Throwable t) {
                Log.v(TAG, "에러:" + t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case MakeQuestionActivity.RESULT_UPDATE_OK:

                Intent intent1 = getIntent();
                String category = intent1.getStringExtra("category");

                String contents = Objects.requireNonNull(data).getStringExtra("content");
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
                    NewQuestionDto newQuestionDto = new NewQuestionDto(contents, category, type, choices, false);

                    // local 퀘션 추가
                    setQuestion(questionList);
                    questionList.add(newQuestionDto);

                    questionAdapter.notifyDataSetChanged();
                }
                break;

            case DeleteQuestionActivity.RESULT_DELETE_OK:

                questionList = (ArrayList<NewQuestionDto>) Objects.requireNonNull(data).getSerializableExtra("delete");
                setQuestion(questionList);
                questionAdapter.setQuestionList(questionList);

                break;
        }
    }

    private void setQuestion(ArrayList<NewQuestionDto> localList) {
        SharedPreferences prefs = getSharedPreferences("pref", 0);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(localList);
        editor.putString("qlist", json);
        editor.apply();
    }


    private ArrayList<NewQuestionDto> getQuestion() {
        SharedPreferences prefs = getSharedPreferences("pref", 0);
        Gson gson = new Gson();
        String json = prefs.getString("qlist", "");
        Type type = new TypeToken<ArrayList<NewQuestionDto>>() {
        }.getType();
        ArrayList<NewQuestionDto> list = gson.fromJson(json, type);
        return list;
    }
}
