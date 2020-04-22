package com.mashup.nnaa.main.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mashup.nnaa.LoginActivity;
import com.mashup.nnaa.R;
import com.mashup.nnaa.main.MainActivity;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.util.AccountManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwActivity extends AppCompatActivity {

    private EditText currentPw, changePw, confirmPw;
    private ImageView img_close;
    private Button btn_change;
    private String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
    private String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        currentPw = findViewById(R.id.edit_current_pw);
        changePw = findViewById(R.id.edit_change_pw);
        confirmPw = findViewById(R.id.edit_change_pw_confirm);
        btn_change = findViewById(R.id.btn_change);
        img_close = findViewById(R.id.img_change_close);

        img_close.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });

        btn_change.setOnClickListener(view -> {
            RetrofitHelper.getInstance().changePw(id, token, currentPw.getText().toString(), changePw.getText().toString(), confirmPw.getText().toString(), new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        btn_change.setBackgroundColor(Color.BLUE);
                        Toast.makeText(view.getContext(), "비밀번호 변경완료! 다시 로그인 해주세요.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        startActivity(intent);

                        Log.v("비번변경", "response:" + response.code() + changePw.getText().toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(view.getContext(), "비밀번호 변경실패", Toast.LENGTH_SHORT);
                }
            });
        });
    }
}
