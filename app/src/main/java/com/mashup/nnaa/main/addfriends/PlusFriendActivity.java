package com.mashup.nnaa.main.addfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.FriendDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlusFriendActivity extends AppCompatActivity {

    ImageView imgClose;
    EditText editName, editEmail;
    Button btnPlus;
    Spinner spinnerPlus;
    public static final int FRIEND_UPDATE_OK = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_friend);

        imgClose = findViewById(R.id.img_plus_close);
        editName = findViewById(R.id.edit_pluse_name);
        editEmail = findViewById(R.id.edit_pluse_email);
        spinnerPlus = findViewById(R.id.spinner_plus);
        btnPlus = findViewById(R.id.btn_plus);

        imgClose.setOnClickListener(v -> finish());

        btnPlus.setOnClickListener(view -> {

            Intent intent = new Intent();
            String name = editName.getText().toString();
            String email = editEmail.getText().toString();
            String category = spinnerPlus.getSelectedItem().toString();

            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("category", category);

            setResult(RESULT_OK, intent);
            finish();

            btnPlus.setBackgroundColor(Color.BLUE);
            Toast.makeText(this, "친구 추가 완료!", Toast.LENGTH_SHORT).show();
        });
    }
}
