package com.mashup.nnaa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SetTypeOfFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_type_of_friend);


        //Spinner
        Spinner friendTypeSpinner = (Spinner)findViewById(R.id.spnTypeOfFriend);
        ArrayAdapter friendTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_friend,android.R.layout.simple_spinner_item);
        friendTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        friendTypeSpinner.setAdapter(friendTypeAdapter);

        //NextButton
        Button nextbtninTOF = (Button)findViewById(R.id.nextbtninTOF);

    }
}
