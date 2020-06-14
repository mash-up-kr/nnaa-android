package com.mashup.nnaa.main.addfriends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.FriendDto;
import com.mashup.nnaa.network.model.Questionnaire;
import com.mashup.nnaa.question.SharingActivity;
import com.mashup.nnaa.util.SharingAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendFriendActivity extends AppCompatActivity {

    ImageView sendClose, searchImg;
    EditText searchView;
    private SendFriendAdapter sendFriendAdapter;
    private ArrayList<FriendDto> sList;
    private ArrayList<FriendDto> list;
    ArrayList<String> name_list = new ArrayList<>();
    ArrayList<String> body_list = new ArrayList<>();
    HashMap<String, String> hashMap = new HashMap<>();
    HashMap<String, String> hashMap1 = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_friend);

        sendClose = findViewById(R.id.img_send_close);
        searchView = findViewById(R.id.send_searchview);
        searchImg = findViewById(R.id.seacrh_img);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Intent intent = getIntent();
        String cateogry = intent.getStringExtra("category");
        String json = intent.getStringExtra("list");

        Intent intent1 = new Intent(SendFriendActivity.this, SendFriendAdapter.class);
        intent1.putExtra("category", cateogry);
        intent1.putExtra("list", json);

        sendClose.setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerview_send);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setHasFixedSize(true);

        sList = new ArrayList<>();
        sList = (ArrayList<FriendDto>) intent.getSerializableExtra("friend_list");
        sendFriendAdapter = new SendFriendAdapter(this, sList);
        recyclerView.setAdapter(sendFriendAdapter);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                sendFriendAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
