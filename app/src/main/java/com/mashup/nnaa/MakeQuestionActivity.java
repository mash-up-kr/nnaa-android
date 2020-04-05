package com.mashup.nnaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeQuestionActivity extends AppCompatActivity {

    private NewQuestionDto newQu = new NewQuestionDto();
    Button CustomDone;
    ImageButton Custom_J, Custom_G, Custom_OX, Custom_J_Blue, Custom_G_Blue, Custom_OX_Blue;
    TextView txtJ, txtG, txtOX, txt_category;
    EditText Content_Edit, FirstEdit, SecondEdit, ThirdEdit, ForthEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_question);

        CustomDone = findViewById(R.id.custom_done);
        Custom_J = findViewById(R.id.custom_btn_j);
        Custom_G = findViewById(R.id.custom_btn_g);
        Custom_OX = findViewById(R.id.custom_btn_ox);

        Custom_J_Blue = findViewById(R.id.custom_btn_j_blue);
        Custom_G_Blue = findViewById(R.id.custom_btn_g_blue);
        Custom_OX_Blue = findViewById(R.id.custom_btn_ox_blue);

        txt_category = findViewById(R.id.custom_category);
        txtJ = findViewById(R.id.txt_j);
        txtG = findViewById(R.id.txt_g);
        txtOX = findViewById(R.id.txt_o);
        Content_Edit = findViewById(R.id.custom_first_edit);
        FirstEdit = findViewById(R.id.custom_choice_edit1);
        SecondEdit = findViewById(R.id.custom_choice_edit2);
        ThirdEdit = findViewById(R.id.custom_choice_edit3);
        ForthEdit = findViewById(R.id.custom_choice_edit4);

        this.setBtn();

        Custom_J.setOnClickListener(view -> {
            Custom_J.setVisibility(View.INVISIBLE);
            Custom_J_Blue.setVisibility(View.VISIBLE);
            Custom_OX.setEnabled(false);
            Custom_G.setEnabled(false);
            Toast.makeText(view.getContext(), "주관식 선택!", Toast.LENGTH_SHORT).show();
        });
        Custom_J_Blue.setOnClickListener(view -> {
            Custom_J_Blue.setVisibility(View.INVISIBLE);
            Custom_J.setVisibility(View.VISIBLE);
            Custom_OX_Blue.setEnabled(true);
            Custom_G.setEnabled(true);
        });
        Custom_G.setOnClickListener(view -> {
            Custom_G.setVisibility(View.INVISIBLE);
            Custom_G_Blue.setVisibility(View.VISIBLE);
            Custom_J.setEnabled(false);
            Custom_OX.setEnabled(false);
            Toast.makeText(view.getContext(), "객관식 선택!", Toast.LENGTH_SHORT).show();
        });
        Custom_G_Blue.setOnClickListener(view -> {
            Custom_G_Blue.setVisibility(View.INVISIBLE);
            Custom_G.setVisibility(View.VISIBLE);
            Custom_J.setEnabled(true);
            Custom_OX.setEnabled(true);
        });
        Custom_OX.setOnClickListener(view -> {
            Custom_OX.setVisibility(View.INVISIBLE);
            Custom_OX_Blue.setVisibility(View.VISIBLE);
            Custom_G.setEnabled(false);
            Custom_J.setEnabled(false);
            Toast.makeText(view.getContext(), "O X 선택!", Toast.LENGTH_SHORT).show();
        });
        Custom_OX_Blue.setOnClickListener(view -> {
            Custom_OX_Blue.setVisibility(View.INVISIBLE);
            Custom_OX.setVisibility(View.VISIBLE);
            Custom_J.setEnabled(true);
            Custom_G.setEnabled(true);
        });

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String category = intent.getStringExtra("type");
            txt_category.setText(category);
        }
        CustomDone.setOnClickListener(view -> {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("a", FirstEdit.getText().toString());
                    jsonObject.addProperty("b", SecondEdit.getText().toString());
                    jsonObject.addProperty("c", ThirdEdit.getText().toString());
                    jsonObject.addProperty("d", ForthEdit.getText().toString());
                    NewQuestionDto newQu = new NewQuestionDto();
                    newQu.setContent(Content_Edit.getText().toString());
                    newQu.setCategory(txt_category.getText().toString());
                    newQu.setChoices(jsonObject);
                    newQu.setType(setBtn());
                    RetrofitHelper.getInstance().postQuestion(newQu, new Callback<NewQuestionDto>() {
                        @Override
                        public void onResponse(Call<NewQuestionDto> call, Response<NewQuestionDto> response) {
                            Log.v("질문 직접 생성", String.valueOf(response.code()));
                            Toast.makeText(MakeQuestionActivity.this, "질문 작성 완료!", Toast.LENGTH_SHORT).show();
                            CustomDone.setBackgroundColor(Color.BLUE);
                        }

                        @Override
                        public void onFailure(Call<NewQuestionDto> call, Throwable t) {
                            Log.v("질문 직접 생성 실패", t.getMessage());
                        }
                    });
                }
        );
    }

    public void onClick(View v) {
        if (v.getId() == R.id.custom_btn_g) {
        }
    }


    private String setBtn() {
        View.OnClickListener listener = view -> {
            switch (view.getId()) {
                case R.id.custom_btn_g:
                    Toast.makeText(MakeQuestionActivity.this, "ㅇㅇㅇㅇ", Toast.LENGTH_SHORT).show();
                    newQu.setType("객관식");
                    break;
                case R.id.custom_btn_j:
                    newQu.setType("주관식");
                    break;
                case R.id.custom_btn_ox:
                    newQu.setType("OX");
                    break;
            }
        };

        Custom_J.setOnClickListener(listener);
        Custom_G.setOnClickListener(listener);
        Custom_OX.setOnClickListener(listener);


//        Custom_J.setOnClickListener(view -> {
//            Toast.makeText(MakeQuestionActivity.this, "ㅇㅇㅇㅇ", Toast.LENGTH_SHORT).show();
//        });
//        return txtJ.getText().toString();

        return null;
    }

}
