package com.mashup.nnaa;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SelectRelationActivity extends AppCompatActivity implements View.OnClickListener{
    TextView friendType;
    ImageButton selectBtnLeft, selectBtnRight;
    ArrayList<String> type = new ArrayList<>();
    Button cancleBtn, nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_relation);

        friendType = findViewById(R.id.type_of_friend);
        selectBtnLeft = findViewById(R.id.rel_btn_l);
        selectBtnRight = findViewById(R.id.rel_btn_r);
        selectBtnLeft.setOnClickListener(this);
        selectBtnRight.setOnClickListener(this);

        cancleBtn = findViewById(R.id.cancle_btn_in_select_relation);
        nextBtn = findViewById(R.id.next_btn_in_select_relation);

        Resources res = getResources();
        type.add(res.getString(R.string.mother));
        type.add(res.getString(R.string.father));
        type.add(res.getString(R.string.sister));
        type.add(res.getString(R.string.brother));
        type.add(res.getString(R.string.younger));
        type.add(res.getString(R.string.friend));
        type.add(res.getString(R.string.lover));
        type.add(res.getString(R.string.vip));


        String yourname = "슬기";
        String iswhom = String.format(res.getString(R.string.you_are_my), yourname, yourname);
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
