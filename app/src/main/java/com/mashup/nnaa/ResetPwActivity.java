package com.mashup.nnaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mashup.nnaa.network.RetrofitHelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPwActivity extends AppCompatActivity {

    ImageView resetClose;
    EditText edit_reset_pw, edit_reset_pw_confirm;
    Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pw);

        edit_reset_pw = findViewById(R.id.edit_reset_pw);
        edit_reset_pw_confirm = findViewById(R.id.edit_reset_pw_confirm);
        resetClose = findViewById(R.id.img_reset_close);
        btn_reset = findViewById(R.id.btn_reset);

        String newPw = edit_reset_pw.getText().toString();
        String newPwConfirm = edit_reset_pw_confirm.getText().toString();

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        RetrofitHelper.getInstance().resetPw(newPw, newPwConfirm, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
