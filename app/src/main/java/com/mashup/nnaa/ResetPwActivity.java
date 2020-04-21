package com.mashup.nnaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pw);

        edit_reset_pw = findViewById(R.id.edit_reset_pw);
        edit_reset_pw_confirm = findViewById(R.id.edit_reset_pw_confirm);
        resetClose = findViewById(R.id.img_reset_close);
        btn_reset = findViewById(R.id.btn_reset);

        /*Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        if (appLinkData != null) {
            if ("http".equals(appLinkData.getScheme()) && "www.nnaa.com".equals(appLinkData.getHost())) {
                appLinkAction = appLinkData.getQueryParameter("userId");
                Log.v("DEEPLINK", "userid:"+ appLinkAction);
            }
        }*/
        // parameter 확인
        Intent deeplink = getIntent();
        if (Intent.ACTION_VIEW.equals(deeplink.getAction())) {
            Uri uri = deeplink.getData();
            String user_id = uri.getQueryParameter("user_id");
            Log.v("DEEPLINK", "userid:" + user_id);
        }

        RetrofitHelper.getInstance().resetPw(edit_reset_pw.getText().toString(), edit_reset_pw_confirm.getText().toString(), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Intent success = new Intent(ResetPwActivity.this, LoginActivity.class);
                    startActivity(success);
                    Toast.makeText(getApplicationContext(), "비밀번호 재설정 완료!", Toast.LENGTH_SHORT).show();
                    Log.v("@@@@@@@@@@@", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
