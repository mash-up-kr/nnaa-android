package com.mashup.nnaa.question;

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

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.Choices;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.util.AccountManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeQuestionActivity extends AppCompatActivity {

    Button CustomDone;
    ImageButton Custom_OX, Custom_J_Blue, Custom_G_Blue, Custom_OX_Blue, Custom_J, Custom_G;
    TextView txtJ, txtG, txtOX;
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

        txtJ = findViewById(R.id.txt_j);
        txtG = findViewById(R.id.txt_g);
        txtOX = findViewById(R.id.txt_o);
        Content_Edit = findViewById(R.id.custom_first_edit);
        FirstEdit = findViewById(R.id.custom_choice_edit1);
        SecondEdit = findViewById(R.id.custom_choice_edit2);
        ThirdEdit = findViewById(R.id.custom_choice_edit3);
        ForthEdit = findViewById(R.id.custom_choice_edit4);

        // QuestionActivity에 줄 값들
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        String type = intent.getStringExtra("type");
        String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
        String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();
        String name = intent.getStringExtra("name");

        NewQuestionDto newQu = new NewQuestionDto();
        Custom_J.setOnClickListener(view -> {
            Custom_J.setVisibility(View.INVISIBLE);
            Custom_J_Blue.setVisibility(View.VISIBLE);
            Custom_OX.setEnabled(false);
            Custom_G.setEnabled(false);
            Toast.makeText(view.getContext(), "주관식 선택!", Toast.LENGTH_SHORT).show();
            FirstEdit.setEnabled(false);
            SecondEdit.setEnabled(false);
            ThirdEdit.setEnabled(false);
            ForthEdit.setEnabled(false);
            newQu.setType("주관식");
        });
        Custom_J_Blue.setOnClickListener(view -> {
            Custom_J_Blue.setVisibility(View.INVISIBLE);
            Custom_J.setVisibility(View.VISIBLE);
            Custom_OX_Blue.setEnabled(true);
            Custom_G.setEnabled(true);
            Toast.makeText(view.getContext(), "주관식 선택 취소!", Toast.LENGTH_SHORT).show();
            FirstEdit.setEnabled(true);
            SecondEdit.setEnabled(true);
            ThirdEdit.setEnabled(true);
            ForthEdit.setEnabled(true);
            newQu.setType("");
        });
        Custom_G.setOnClickListener(view -> {
            Custom_G.setVisibility(View.INVISIBLE);
            Custom_G_Blue.setVisibility(View.VISIBLE);
            Custom_J.setEnabled(false);
            Custom_OX.setEnabled(false);
            Toast.makeText(view.getContext(), "객관식 선택!", Toast.LENGTH_SHORT).show();
            newQu.setType("객관식");
        });
        Custom_G_Blue.setOnClickListener(view -> {
            Custom_G_Blue.setVisibility(View.INVISIBLE);
            Custom_G.setVisibility(View.VISIBLE);
            Custom_J.setEnabled(true);
            Custom_OX.setEnabled(true);
            Toast.makeText(view.getContext(), "객관식 선택 취소!", Toast.LENGTH_SHORT).show();
            newQu.setType("");
        });
        Custom_OX.setOnClickListener(view -> {
            Custom_OX.setVisibility(View.INVISIBLE);
            Custom_OX_Blue.setVisibility(View.VISIBLE);
            Custom_G.setEnabled(false);
            Custom_J.setEnabled(false);
            FirstEdit.setEnabled(false);
            SecondEdit.setEnabled(false);
            ThirdEdit.setEnabled(false);
            ForthEdit.setEnabled(false);
            Toast.makeText(view.getContext(), "O X 선택!", Toast.LENGTH_SHORT).show();
            newQu.setType("OX");
        });
        Custom_OX_Blue.setOnClickListener(view -> {
            Custom_OX_Blue.setVisibility(View.INVISIBLE);
            Custom_OX.setVisibility(View.VISIBLE);
            Custom_J.setEnabled(true);
            Custom_G.setEnabled(true);
            FirstEdit.setEnabled(true);
            SecondEdit.setEnabled(true);
            ThirdEdit.setEnabled(true);
            ForthEdit.setEnabled(true);
            Toast.makeText(view.getContext(), "O X 선택 취소!", Toast.LENGTH_SHORT).show();
            newQu.setType("");
        });

        CustomDone.setOnClickListener(view -> {
                    Choices choices = new Choices();
                    choices.setA(FirstEdit.getText().toString());
                    choices.setB(SecondEdit.getText().toString());
                    choices.setC(ThirdEdit.getText().toString());
                    choices.setD(ForthEdit.getText().toString());
                    newQu.setContent(Content_Edit.getText().toString());
                    newQu.setCategory(category);
                    newQu.setChoices(choices);
                    RetrofitHelper.getInstance().postQuestion(newQu, new Callback<NewQuestionDto>() {
                        @Override
                        public void onResponse(Call<NewQuestionDto> call, Response<NewQuestionDto> response) {
                            if (response.isSuccessful()) {
                                Log.v("질문 직접 생성", "respose :" + response.code() + "," + "questionId:" + response.body().getId() + "," + "type: " + newQu.getType());
                                CustomDone.setBackgroundColor(Color.BLUE);
                                //launchQuestionActivity();
                                Intent mk_intent = new Intent(MakeQuestionActivity.this, QuestionActivity.class);
                                mk_intent.putExtra("category", category);
                                mk_intent.putExtra("id", id);
                                mk_intent.putExtra("token", token);
                                mk_intent.putExtra("name", name);
                                mk_intent.putExtra("type", type);
                                startActivity(mk_intent);
                                finish();
                            } else if (response.code() == 400) {
                                Toast.makeText(MakeQuestionActivity.this, "질문 세팅을 완료해주세요!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<NewQuestionDto> call, Throwable t) {
                            Log.v("질문 직접 생성 실패", t.getMessage());
                        }
                    });
                }
        );
    }

    private void launchQuestionActivity() {
        Toast.makeText(MakeQuestionActivity.this, "질문 작성 완료!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MakeQuestionActivity.this, QuestionActivity.class);
        startActivity(intent);
        finish();
    }
}
