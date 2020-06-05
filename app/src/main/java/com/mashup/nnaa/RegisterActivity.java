package com.mashup.nnaa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mashup.nnaa.util.AccountManager;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText edit_name = findViewById(R.id.edit_name);
        EditText edit_email = findViewById(R.id.edit_email);
        EditText edit_password_confirm = findViewById(R.id.edit_password_confirm);
        EditText edit_password = findViewById(R.id.edit_password);
        Button btn_register = findViewById(R.id.btn_register);
        ImageView img_register_close = findViewById(R.id.img_register_close);


        img_register_close.setOnClickListener(view -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
            alertDialogBuilder.setMessage("회원가입을 취소하시겠습니까?");
            alertDialogBuilder.setPositiveButton("네", (dialogInterface, i) -> {
                Toast.makeText(RegisterActivity.this, "회원가입을 취소하였습니다!", Toast.LENGTH_SHORT).show();
                Intent backIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(backIntent);
            });
            alertDialogBuilder.setNegativeButton("취소", (dialogInterface, i) -> Toast.makeText(RegisterActivity.this, "회원가입을 작성해주세요!", Toast.LENGTH_SHORT).show());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        btn_register.setOnClickListener(view -> AccountManager.getInstance().executeRegister(edit_email.getText().toString(),
                edit_password.getText().toString(),
                edit_name.getText().toString(),
                new AccountManager.ISignInResultListener() {
                    @Override
                    public void onSignInSuccess(String id, String name, String token) {
                        Log.v("RegisterSuccess", "Success");
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(RegisterActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSignInFail() {
                        Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다!", Toast.LENGTH_SHORT).show();
                        Log.v("RegisterFail", "Fail");
                    }
                }));

        if (edit_name.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
            edit_name.requestFocus();
            return;
        }

        if (edit_email.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "email을 입력해주세요!", Toast.LENGTH_SHORT).show();
            edit_email.requestFocus();
            return;
        }

        if (edit_password.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
            edit_password.requestFocus();
            return;
        }
        if (edit_password_confirm.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "비밀번호 확인란을 입력해주세요!", Toast.LENGTH_SHORT).show();
            edit_password_confirm.requestFocus();
            return;
        }

        if (!edit_password.getText().toString().equals(edit_password_confirm.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
            edit_password.setText("");
            edit_password_confirm.setText("");
            edit_password.requestFocus();
        }
    }
}