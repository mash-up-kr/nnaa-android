package com.mashup.nnaa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.util.EncryptUtil;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPwActivity extends AppCompatActivity {

    private ImageView resetClose;
    private EditText edit_reset_pw, edit_reset_pw_confirm;
    private Button btn_reset;
    private String TAG = "ResetPwActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pw);

        edit_reset_pw = findViewById(R.id.edit_reset_pw);
        edit_reset_pw_confirm = findViewById(R.id.edit_reset_pw_confirm);
        resetClose = findViewById(R.id.img_reset_close);
        btn_reset = findViewById(R.id.btn_reset);

        resetClose.setOnClickListener(view -> finish());

        // parameter 확인
        Intent deeplink = getIntent();
        if (Intent.ACTION_VIEW.equals(deeplink.getAction())) {
            Uri uri = deeplink.getData();
            String user_id = Objects.requireNonNull(uri).getQueryParameter("id");
            String token = Objects.requireNonNull(uri).getQueryParameter("token");
            Log.v(TAG, "id:" + user_id + "," + "token:" + token);

            btn_reset.setOnClickListener(view -> {
                String plainNewPw = edit_reset_pw.getText().toString();
                String plainNewPwConfirm = edit_reset_pw_confirm.getText().toString();

                String encryptNewPw = EncryptUtil.encryptPasswordFromPlaintextToSignIn(plainNewPw);
                String encryptNewPwConfirm = EncryptUtil.encryptPasswordFromPlaintextToSignIn(plainNewPwConfirm);

                RetrofitHelper.getInstance().resetPw(user_id, token, encryptNewPw, encryptNewPwConfirm, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Intent success = new Intent(NnaaApplication.getAppContext(), LoginActivity.class);
                            Toast.makeText(getApplicationContext(), "비밀번호가 재설정 되었습니다.",Toast.LENGTH_SHORT).show();
                            success.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            NnaaApplication.getAppContext().startActivity(success);
                            Log.v(TAG, response.code() + "새로운 비번:" + edit_reset_pw.getText().toString() + "," + "비번 확인:" + edit_reset_pw_confirm.getText().toString());
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "기존 비밀번호와 새로운 비밀번호를 다르게 입력해주세요!.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.v(TAG, t.getMessage());
                    }
                });
            });
        }
    }
}
