package com.mashup.nnaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SetTypeOfFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_type_of_friend);

        //Spinner
        Spinner friendTypeSpinner = findViewById(R.id.spinner_type_of_friend);
        ArrayAdapter friendTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_friend,android.R.layout.simple_spinner_item);
        friendTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        friendTypeSpinner.setAdapter(friendTypeAdapter);

        //NextButton
        Button nextbtninTOF = findViewById(R.id.next_btn_in_type_of_friend);

        Resources res = getResources();
        String yourname = "슬기짱짱";
        String iswhom = String.format(res.getString(R.string.you_are_my), yourname, yourname);
        TextView youaremy = findViewById(R.id.you_are_my);
        youaremy.setText(iswhom);
    }
}
