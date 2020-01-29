package com.mashup.nnaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.icu.text.Edits;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SetTypeOfFriendActivity extends AppCompatActivity implements View.OnClickListener{
    TextView friendType;
    ImageButton selectBtnLeft, selectBtnRight;
    ArrayList<String> type = new ArrayList<>();
    Button cancleBtn, nextBtn;

    String name = "";
    String number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_type_of_friend);

        friendType = findViewById(R.id.type_of_friend);
        selectBtnLeft = findViewById(R.id.rel_btn_l);
        selectBtnRight = findViewById(R.id.rel_btn_r);
        selectBtnLeft.setOnClickListener(this);
        selectBtnRight.setOnClickListener(this);

        cancleBtn = findViewById(R.id.cancle_btn_in_type_of_friend);
        nextBtn = findViewById(R.id.next_btn_in_type_of_friend);

        Resources res = getResources();
        type.add(res.getString(R.string.mother));
        type.add(res.getString(R.string.father));
        type.add(res.getString(R.string.sister));
        type.add(res.getString(R.string.brother));
        type.add(res.getString(R.string.younger));
        type.add(res.getString(R.string.friend));
        type.add(res.getString(R.string.lover));
        type.add(res.getString(R.string.vip));

        // load friend info
        name = getIntent().getStringExtra("name");
        number = getIntent().getStringExtra("number");


        String iswhom = String.format(res.getString(R.string.you_are_my), name, name);
        TextView youaremy = findViewById(R.id.you_are_my);
        youaremy.setText(iswhom);

        cancleBtn.setOnClickListener((view)-> {
            finish();
        });
    }

    @Override
    public void onClick(View view) {
        int ind = type.indexOf(friendType.getText());

        switch(view.getId()) {
            case R.id.rel_btn_l:
                if(ind==0) ind = type.size()-1;
                else ind = ind-1;
                friendType.setText(type.get(ind));
                break;
            case R.id.rel_btn_r:
                if(ind==type.size()-1) ind = 0;
                else ind = ind+1;
                friendType.setText(type.get(ind));
                break;
            default:
                break;
        }
    }
}
