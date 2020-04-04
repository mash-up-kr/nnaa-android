package com.mashup.nnaa.question;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashup.nnaa.CustomQuestionCallback;
import com.mashup.nnaa.R;

import com.mashup.nnaa.data.Choices;
import com.mashup.nnaa.data.QuestionItem;
import com.mashup.nnaa.network.QuestionControllerService;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.util.CustomQuestionAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mashup.nnaa.util.CustomQuestionAdapter.FIRST_CONTENT;
import static com.mashup.nnaa.util.CustomQuestionAdapter.FORTH_CONTENT;
import static com.mashup.nnaa.util.CustomQuestionAdapter.SECOND_CONTENT;
import static com.mashup.nnaa.util.CustomQuestionAdapter.THIRD_CONTENT;

public class CustomQuestionActivity extends AppCompatActivity {

    ImageButton imgbtn_past, imgbtn_cancel;
    Button btn_done;
    ImageView img_recycler;
    private CustomQuestionAdapter customQuestionAdapter;
    private ArrayList<QuestionItem> cArrayList;
    private NewQuestionDto newQu = new NewQuestionDto();
    Context mContext;
    private CustomQuestionCallback callback =
            pos -> Toast.makeText(CustomQuestionActivity.this, "클릭", Toast.LENGTH_SHORT).show();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_question);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String type = intent.getStringExtra("type");
        }

        imgbtn_past = findViewById(R.id.imgbtn_past);
        imgbtn_cancel = findViewById(R.id.imgbtn_cancel);
        btn_done = findViewById(R.id.btn_done);
        img_recycler = findViewById(R.id.img_recycler);

        this.initializeData();

        imgbtn_past.setOnClickListener(view -> {
            Intent past_intent = new Intent(CustomQuestionActivity.this, FavoritesActivity.class);
            startActivity(past_intent);
        });

        imgbtn_cancel.setOnClickListener(view -> {
            Intent cancel_intent = new Intent(CustomQuestionActivity.this, FavoritesActivity.class);
            startActivity(cancel_intent);
        });

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("a", "a");

        btn_done.setOnClickListener(view -> {
            NewQuestionDto newQu = new NewQuestionDto();
            newQu.setContent(String.valueOf(callback));
            newQu.setCategory("친구");
            newQu.setChoices(jsonObject);
            newQu.setType("객관식");
                    RetrofitHelper.getInstance().postQuestion(newQu, new Callback<NewQuestionDto>() {
                        @Override
                        public void onResponse(Call<NewQuestionDto> call, Response<NewQuestionDto> response) {
                            Log.v("질문 직접 생성", String.valueOf(response.code()));
                            Toast.makeText(CustomQuestionActivity.this, "질문 작성 완료!", Toast.LENGTH_SHORT).show();
                            btn_done.setBackgroundColor(Color.BLUE);
                        }

                        @Override
                        public void onFailure(Call<NewQuestionDto> call, Throwable t) {
                            Log.v("질문 직접 생성 실패", t.getMessage());
                        }
                    });
                }
        );
    }

    private void initializeData() {

        RecyclerView recyclerCustom = findViewById(R.id.custom_recycler);
        recyclerCustom.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerCustom.setLayoutManager(linearLayoutManager);
        cArrayList = new ArrayList<>();
        customQuestionAdapter = new CustomQuestionAdapter(this, cArrayList);
        recyclerCustom.setAdapter(customQuestionAdapter);

        customQuestionAdapter.setCallback(callback);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        cArrayList.add(new QuestionItem("첫번쨰, 원하는 질문을 입력해주세요", "", R.id.img_add, FIRST_CONTENT));
        cArrayList.add(new QuestionItem("두번째, 문제 형식을 설정해주세요", "", R.id.img_how_item, SECOND_CONTENT));
        cArrayList.add(new QuestionItem("세번째, 보기를 설정해주세요", "", R.id.img_add, THIRD_CONTENT));
        cArrayList.add(new QuestionItem("짝짝짝 질문 생성 완료!", "", R.id.img_add, FORTH_CONTENT));
    }

    private String setBtn() {
        View v = LayoutInflater.from(CustomQuestionActivity.this).inflate(R.layout.custom_first, null, false);
        final TextView txtJ = v.findViewById(R.id.txt_j);
        final TextView txtG = v.findViewById(R.id.txt_g);
        final TextView txtOX = v.findViewById(R.id.txt_o);

        txtJ.setOnClickListener(view -> {
            assert view != null;
            txtJ.getText().toString();
        });
//        final Button btnJ = v.findViewById(R.id.btn_j);
//        final Button btnG = v.findViewById(R.id.btn_g);
//        final Button btnOX = v.findViewById(R.id.btn_o);
//
//        btnJ.setOnClickListener(view -> {
//            if (view != null) {
//                btnJ.getText().toString();
//                btnG.setEnabled(false);
//                btnOX.setEnabled(false);
//            }
//        });
//        btnG.setOnClickListener(view -> {
//            if (view != null) {
//                btnG.getText().toString();
//                btnJ.setEnabled(false);
//                btnOX.setEnabled(false);
//            }
//        });
//        btnOX.setOnClickListener(view -> {
//            if (view != null) {
//                btnOX.getText().toString();
//                btnG.setEnabled(false);
//                btnJ.setEnabled(false);
//            }
//        });
        return null;
    }

    private String[] setChoice() {
        View v = LayoutInflater.from(CustomQuestionActivity.this).inflate(R.layout.custom_third, null, false);
        EditText editText1 = v.findViewById(R.id.edit1);
        EditText editText2 = v.findViewById(R.id.edit2);
        EditText editText3 = v.findViewById(R.id.edit3);
        EditText editText4 = v.findViewById(R.id.edit4);

        if (editText1.getText().toString().length() == 0) {
            editText1.setText("");
        } else if (editText1.getText().toString().length() != 0) {
            editText1.getText().toString();
        }
        if (editText2.getText().toString().length() == 0) {
            editText2.setText("");
        } else if (editText2.getText().toString().length() != 0) {
            editText2.getText().toString();
        }
        if (editText3.getText().toString().length() == 0) {
            editText3.setText("");
        } else if (editText3.getText().toString().length() != 0) {
            editText3.getText().toString();
        }
        if (editText4.getText().toString().length() == 0) {
            editText4.setText("");
        } else if (editText4.getText().toString().length() != 0) {
            editText4.getText().toString();
        }

        return null;
    }

}
