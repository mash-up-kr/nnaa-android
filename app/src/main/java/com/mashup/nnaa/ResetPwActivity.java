package com.mashup.nnaa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
    }
}
