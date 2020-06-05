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
import android.widget.Toast;

import com.mashup.nnaa.network.RetrofitHelper;

import okhttp3.ResponseBody;
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


        btnFind.setOnClickListener(view -> {
            if (editFind.getText().toString().isEmpty()) {
                Toast.makeText(FindPwActivity.this, "가입하신 이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.setMessage("메일을 보내는중입니다..");
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
                progressDialog.show();

                RetrofitHelper.getInstance().sendNewPw(editFind.getText().toString(), new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            btnFind.setBackgroundColor(Color.BLUE);
                            Toast.makeText(FindPwActivity.this, "이메일을 보냈습니다!", Toast.LENGTH_SHORT).show();
                            Log.v("재설정 이메일", "response:" + response.code() + editFind.getText().toString());
                        }
                        else {
                            Toast.makeText(FindPwActivity.this, "(회원정보 오류) 가입했던 이메일을 확인해주세요!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.v("재설정 이메일", t.getMessage());
                    }
                });
            }
        });
    }
}
