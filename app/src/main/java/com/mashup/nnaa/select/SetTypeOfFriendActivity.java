package com.mashup.nnaa.select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mashup.nnaa.R;
import com.mashup.nnaa.question.QuestionActivity;

import java.util.ArrayList;
import java.util.Collections;

public class SetTypeOfFriendActivity extends AppCompatActivity implements View.OnClickListener {
    TextView friendType;
    ImageButton selectBtnLeft, selectBtnRight;
    ArrayList<String> type = new ArrayList<>();
    Button nextBtn;
    ImageView cancleBtn;

    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_type_of_friend);

        friendType = findViewById(R.id.type_of_friend);
        selectBtnLeft = findViewById(R.id.rel_btn_l);
        selectBtnRight = findViewById(R.id.rel_btn_r);
        selectBtnLeft.setOnClickListener(this);
        selectBtnRight.setOnClickListener(this);

        etName = findViewById(R.id.et_name);

        cancleBtn = findViewById(R.id.cancle_btn_in_type_of_friend);
        nextBtn = findViewById(R.id.next_btn_in_type_of_friend);

        cancleBtn.setOnClickListener((view) -> finish());

        Intent intent1 = getIntent();
        if(intent1!=null) {
            etName.setText(intent1.getStringExtra("friend_name"));
        }
        nextBtn.setOnClickListener(view -> {
            String name = etName.getText().toString().trim();
            String typename = friendType.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(this, R.string.plz_enter_name, Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
            intent.putExtra("name", name + "님");
            intent.putExtra("type", typename);
            intent.putExtra("category", typename);

            startActivity(intent);

        });

        String[] type_str = getResources().getStringArray(R.array.questionlist);
        Collections.addAll(type, type_str);

    }

    @Override
    public void onClick(View view) {
        int ind = type.indexOf(friendType.getText());

        switch (view.getId()) {
            case R.id.rel_btn_l:
                if (ind == 0) ind = type.size() - 1;
                else ind = ind - 1;
                friendType.setText(type.get(ind));
                break;
            case R.id.rel_btn_r:
                if (ind == type.size() - 1) ind = 0;
                else ind = ind + 1;
                friendType.setText(type.get(ind));
                break;
            default:
                break;
        }
    }
}