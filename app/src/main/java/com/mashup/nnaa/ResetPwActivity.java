package com.mashup.nnaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.util.AccountManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPwActivity extends AppCompatActivity {

    private ImageView resetClose;
    private EditText edit_reset_pw, edit_reset_pw_confirm;
    private Button btn_reset;
    private String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();

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

        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        RetrofitHelper.getInstance().resetPw(newPw, newPwConfirm, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Intent success = new Intent(ResetPwActivity.this, LoginActivity.class);
                    startActivity(success);
                    Toast.makeText(getApplicationContext(), "비밀번호 재설정 완료!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
