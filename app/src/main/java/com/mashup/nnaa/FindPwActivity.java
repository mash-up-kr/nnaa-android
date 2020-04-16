package com.mashup.nnaa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.LoginDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        String email = editFind.getText().toString();

        btnFind.setOnClickListener(view -> {
            if (editFind.getText().toString().isEmpty()) {
                Toast.makeText(FindPwActivity.this, "가입하신 이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
            } else {
                RetrofitHelper.getInstance().sendNewPw(email, new Callback<LoginDto>() {
                    @Override
                    public void onResponse(Call<LoginDto> call, Response<LoginDto> response) {
                        progressDialog.setMessage("메일을 보내는중입니다..");
                        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
                        progressDialog.show();
                        if (response.isSuccessful()) {
                            btnFind.setBackgroundColor(Color.BLUE);
                            Toast.makeText(FindPwActivity.this, "이메일을 보냈습니다!", Toast.LENGTH_SHORT).show();
                            Log.v("재설정 이메일", email);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginDto> call, Throwable t) {
                        Log.v("재설정 이메일", t.getMessage());
                    }
                });
            }
            progressDialog.dismiss();
        });
    }
}
