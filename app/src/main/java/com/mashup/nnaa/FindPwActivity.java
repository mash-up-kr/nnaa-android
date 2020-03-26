package com.mashup.nnaa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FindPwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        EditText editFind = findViewById(R.id.edit_find);
        ImageView findClose = findViewById(R.id.img_find_close);
        Button btnFind = findViewById(R.id.btn_find);
        ProgressDialog progressDialog = new ProgressDialog(this);

        findClose.setOnClickListener(view -> {
            Intent intent = new Intent(FindPwActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnFind.setOnClickListener(view -> {
            if (editFind.getText().toString().isEmpty()) {
                Toast.makeText(FindPwActivity.this, "가입하신 이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.setMessage("메일을 보내는중입니다..");
                progressDialog.show();
                Toast.makeText(FindPwActivity.this, "이메일로 비밀번호를 보냈습니다!", Toast.LENGTH_SHORT).show();
                btnFind.setBackgroundColor(Color.BLUE);
            }
            //progressDialog.dismiss();
        });
    }
}
