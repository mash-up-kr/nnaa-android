package com.mashup.nnaa.main.addfriends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mashup.nnaa.R;
import com.mashup.nnaa.network.model.FriendDto;
import com.mashup.nnaa.select.SetTypeOfFriendActivity;
import com.mashup.nnaa.util.AccountManager;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    TextView myName, noFriend;
    ImageView friendClose, btnClose;
    Button nextBtn;
    FloatingActionButton fabMain, fabSub1, fabSub2;
    Animation fabOpen, fabClose;
    private boolean isFabOpen = false;
    private AddFriendAdapter addFriendAdapter;
    private ArrayList<FriendDto> friendList;
    String name = AccountManager.getInstance().getUserAuthHeaderInfo().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        mContext = getApplicationContext();

        myName = findViewById(R.id.addfriend_type);
        friendClose = findViewById(R.id.addfriend_close);
        btnClose = findViewById(R.id.btn_addfriend_cancel);
        nextBtn = findViewById(R.id.btn_addfriend_next);
        noFriend = findViewById(R.id.txt_nofriend);
        fabMain = findViewById(R.id.fab_main);
        fabSub1 = findViewById(R.id.fab_sub1);
        fabSub2 = findViewById(R.id.fab_sub2);

        fabMain.setOnClickListener(this);
        fabSub1.setOnClickListener(this);
        fabSub2.setOnClickListener(this);
        fabOpen = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(mContext, R.anim.fab_down);

        myName.setText(String.format("%s님의", name));

        nextBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SetTypeOfFriendActivity.class);
            startActivity(intent);
        });

        friendClose.setOnClickListener(view -> finish());
        btnClose.setOnClickListener(view -> finish());

        RecyclerView recyclerView = findViewById(R.id.recycler_friend);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setHasFixedSize(true);

        friendList = new ArrayList<>();
        addFriendAdapter = new AddFriendAdapter(this, friendList);
        if(addFriendAdapter.getItemCount() == 0) {
            noFriend.setVisibility(View.VISIBLE);
        } else {
            noFriend.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(addFriendAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_main:
                toggleFab();
                break;
            case R.id.fab_sub1:
                toggleFab();
                Intent intent = new Intent(v.getContext(), PlusFriendActivity.class);
                startActivity(intent);
                Toast.makeText(this, "친구를 추가하러 가겠습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_sub2:
                toggleFab();
                Intent intent1 = new Intent(v.getContext(), DeleteFriendActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "친구를 삭제하러 가겠습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void toggleFab() {
        if (isFabOpen) {
            fabMain.setImageResource(R.drawable.float_add);
            fabSub1.startAnimation(fabClose);
            fabSub2.startAnimation(fabClose);
            fabSub1.setClickable(false);
            fabSub2.setClickable(false);
        } else {
            fabMain.setImageResource(R.drawable.delete_friend);
            fabSub1.startAnimation(fabOpen);
            fabSub2.startAnimation(fabOpen);
            fabSub1.setClickable(true);
            fabSub2.setClickable(true);
            isFabOpen = true;
        }
    }
}
