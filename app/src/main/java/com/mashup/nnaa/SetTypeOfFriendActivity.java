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
        Spinner friendTypeSpinner = findViewById(R.id.spnTypeOfFriend);
        ArrayAdapter friendTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_friend,android.R.layout.simple_spinner_item);
        friendTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        friendTypeSpinner.setAdapter(friendTypeAdapter);

        //NextButton
        Button nextbtninTOF = findViewById(R.id.nextbtninTOF);

        Resources res = getResources();
        String yourname = "Seulgi";
        String iswhom = String.format(res.getString(R.string.youaremy), yourname);
        TextView youaremy = findViewById(R.id.youaremy);
        youaremy.setText(iswhom);
    }
}
