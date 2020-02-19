package com.mashup.nnaa;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText email_edit, password_edit, password_edit_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email_edit = findViewById(R.id.email_edit);
        password_edit = findViewById(R.id.password_edit);
        password_edit_confirm = findViewById(R.id.password_edit_confirm);
        Button btn_signIn_done = findViewById(R.id.btn_signIn_done);
        Button btn_signIn_cancel = findViewById(R.id.btn_signIn_cancel);

        password_edit_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                String password = password_edit.getText().toString();
                String confirm = password_edit_confirm.getText().toString();

                if(password.equals(confirm)) {
                    password_edit.setBackgroundColor(Color.GREEN);
                    password_edit_confirm.setBackgroundColor(Color.GREEN);
                }
                else {
                    password_edit.setBackgroundColor(Color.RED);
                    password_edit_confirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_signIn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email_edit.getText().toString().length()==0) {
                    Toast.makeText(SignInActivity.this, "Email을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    email_edit.requestFocus();
                    return;
                }

                if(password_edit.getText().toString().length()==0) {
                    Toast.makeText(SignInActivity.this, "Password를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    password_edit.requestFocus();
                    return;
                }
                if(password_edit_confirm.getText().toString().length()==0) {
                    Toast.makeText(SignInActivity.this, "Password Confirm을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    password_edit_confirm.requestFocus();
                    return;
                }

                if(!password_edit.getText().toString().equals(password_edit_confirm.getText().toString())) {
                    Toast.makeText(SignInActivity.this, "Password가 일치하지 않습니다!",Toast.LENGTH_SHORT).show();
                    password_edit.setText("");
                    password_edit_confirm.setText("");
                    password_edit.requestFocus();
                    return;
                }

                Intent doneIntent = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(doneIntent);
            }
        });

        btn_signIn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignInActivity.this);
                alertDialogBuilder.setMessage("회원가입을 취소하시겠습니까?");
                alertDialogBuilder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SignInActivity.this, "회원가입을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent backIntent = new Intent(SignInActivity.this, LoginActivity.class);
                        startActivity(backIntent);
                    }
                });
                alertDialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SignInActivity.this, "회원가입을 작성해주세요!", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}
