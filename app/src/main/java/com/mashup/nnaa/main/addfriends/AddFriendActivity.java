package com.mashup.nnaa.main.addfriends;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mashup.nnaa.R;
import com.mashup.nnaa.data.Choices;
import com.mashup.nnaa.main.MainActivity;
import com.mashup.nnaa.network.model.FriendDto;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.network.model.Questionnaire;
import com.mashup.nnaa.question.DeleteQuestionActivity;
import com.mashup.nnaa.question.MakeQuestionActivity;
import com.mashup.nnaa.select.SetTypeOfFriendActivity;
import com.mashup.nnaa.util.AccountManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

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

        RecyclerView recyclerView = findViewById(R.id.recycler_friend);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setHasFixedSize(true);

        friendList = new ArrayList<>();
        addFriendAdapter = new AddFriendAdapter(this, friendList);
        recyclerView.setAdapter(addFriendAdapter);

        if (getQuestion() != null) {
            for (FriendDto friendDto : getQuestion()) {
                friendList.add(friendDto);
                addFriendAdapter.notifyDataSetChanged();
            }
        }

        friendClose.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra("friend_list", friendList);
            startActivity(intent);
        });
        btnClose.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra("friend_list", friendList);
            startActivity(intent);
        });

        nextBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SetTypeOfFriendActivity.class);
            intent.putExtra("friend_list", friendList);
            startActivity(intent);
        });
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
                startActivityForResult(intent, 10000);
                Toast.makeText(this, "친구를 추가하러 가겠습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_sub2:
                toggleFab();
                Intent intent1 = new Intent(v.getContext(), DeleteFriendActivity.class);
                intent1.putExtra("list", friendList);
                startActivityForResult(intent1, 0);
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
            isFabOpen = false;
        } else {
            fabMain.setImageResource(R.drawable.delete_friend);
            fabSub1.startAnimation(fabOpen);
            fabSub2.startAnimation(fabOpen);
            fabSub1.setClickable(true);
            fabSub2.setClickable(true);
            isFabOpen = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case PlusFriendActivity.RESULT_OK:
                if(data!=null) {
                    Bundle bundle = data.getExtras();
                    String name = Objects.requireNonNull(bundle).getString("name");
                    String email = bundle.getString("email");
                    String category = bundle.getString("category");

                    FriendDto friendDto = new FriendDto(category, name, email);

                    friendList.add(friendDto);

                    addFriendAdapter.notifyDataSetChanged();
                }
                break;

            case DeleteFriendActivity.RESULT_DELETE_OK:

                friendList = (ArrayList<FriendDto>) Objects.requireNonNull(data).getSerializableExtra("delete");
                setQuestion(friendList);
                addFriendAdapter.setFirendList(friendList);
                break;
        }
    }

    private void setQuestion(ArrayList<FriendDto> friendList) {
        SharedPreferences prefs = getSharedPreferences("prefrence", 0);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(friendList);
        editor.putString("flist", json);
        editor.apply();
    }


    private ArrayList<FriendDto> getQuestion() {
        SharedPreferences prefs = getSharedPreferences("prefrence", 0);
        Gson gson = new Gson();
        String json = prefs.getString("flist", "");
        Type type = new TypeToken<ArrayList<FriendDto>>() {
        }.getType();
        ArrayList<FriendDto> arrayList = gson.fromJson(json, type);
        return arrayList;
    }
    @Override
    protected void onPause() {
        super.onPause();
        setQuestion(friendList);
    }
}
