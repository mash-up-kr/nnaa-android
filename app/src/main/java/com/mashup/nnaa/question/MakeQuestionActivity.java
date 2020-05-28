package com.mashup.nnaa.question;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mashup.nnaa.R;

public class MakeQuestionActivity extends AppCompatActivity {

    public static final int RESULT_UPDATE_OK = 10000;
    Button CustomDone;
    ImageButton Custom_OX, Custom_J_Blue, Custom_G_Blue, Custom_OX_Blue, Custom_J, Custom_G;
    TextView txtJ, txtG, txtOX;
    EditText Content_Edit, FirstEdit, SecondEdit, ThirdEdit, ForthEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_question);

        Intent mk_intent = new Intent();

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
            mk_intent.putExtra("type", "주관식");
        });
        Custom_J_Blue.setOnClickListener(view -> {
            Custom_J_Blue.setVisibility(View.INVISIBLE);
            Custom_J.setVisibility(View.VISIBLE);
            Custom_OX.setEnabled(true);
            Custom_G.setEnabled(true);
            Toast.makeText(view.getContext(), "주관식 선택 취소!", Toast.LENGTH_SHORT).show();
            FirstEdit.setEnabled(true);
            SecondEdit.setEnabled(true);
            ThirdEdit.setEnabled(true);
            ForthEdit.setEnabled(true);
            mk_intent.putExtra("type", "");
        });
        Custom_G.setOnClickListener(view -> {
            Custom_G.setVisibility(View.INVISIBLE);
            Custom_G_Blue.setVisibility(View.VISIBLE);
            Custom_J.setEnabled(false);
            Custom_OX.setEnabled(false);
            Toast.makeText(view.getContext(), "객관식 선택!", Toast.LENGTH_SHORT).show();
            mk_intent.putExtra("type", "객관식");
        });
        Custom_G_Blue.setOnClickListener(view -> {
            Custom_G_Blue.setVisibility(View.INVISIBLE);
            Custom_G.setVisibility(View.VISIBLE);
            Custom_J.setEnabled(true);
            Custom_OX.setEnabled(true);
            Toast.makeText(view.getContext(), "객관식 선택 취소!", Toast.LENGTH_SHORT).show();
            mk_intent.putExtra("type", "");
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
            mk_intent.putExtra("type", "OX");
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
            mk_intent.putExtra("type", "");
        });


        CustomDone.setOnClickListener(view -> {

            mk_intent.putExtra("content", Content_Edit.getText().toString());
            mk_intent.putExtra("setA", FirstEdit.getText().toString());
            mk_intent.putExtra("setB", SecondEdit.getText().toString());
            mk_intent.putExtra("setC", ThirdEdit.getText().toString());
            mk_intent.putExtra("setD", ForthEdit.getText().toString());

            setResult(RESULT_UPDATE_OK, mk_intent);
            finish();
            CustomDone.setBackgroundColor(Color.BLUE);
            Toast.makeText(MakeQuestionActivity.this, "질문 작성 완료!", Toast.LENGTH_SHORT).show();

        });
    }
}

