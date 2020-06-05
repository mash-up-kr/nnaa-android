package com.mashup.nnaa.main.addfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mashup.nnaa.R;

public class PlusFriendActivity extends AppCompatActivity {

    ImageView imgClose;
    EditText editName, editEmail;
    Button btnPlus;
    Spinner spinnerPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_friend);

        imgClose = findViewById(R.id.img_plus_close);
        editName = findViewById(R.id.edit_pluse_name);
        editEmail = findViewById(R.id.edit_pluse_email);
        spinnerPlus = findViewById(R.id.spinner_plus);
        btnPlus = findViewById(R.id.btn_plus);

        imgClose.setOnClickListener(v->finish());


        btnPlus.setOnClickListener(view-> {
            Toast.makeText(this,"spiner:" + spinnerPlus.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
        });
    }
}
