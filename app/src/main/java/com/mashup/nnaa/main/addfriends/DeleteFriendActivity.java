package com.mashup.nnaa.main.addfriends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.model.FriendDto;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.ItemTouchHelperCallback;

import java.util.ArrayList;

public class DeleteFriendActivity extends AppCompatActivity {

    TextView yourName;
    ImageView imgCancel;
    Button btnDelete;
    private ItemTouchHelper helper;
    private DeleteFriendAdpater deleteFriendAdpater;
    private ArrayList<FriendDto> deletList;
    private String name = AccountManager.getInstance().getUserAuthHeaderInfo().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_friend);

        yourName = findViewById(R.id.delete_username);
        imgCancel = findViewById(R.id.btn_friend_cancel);
        btnDelete = findViewById(R.id.btn_firend_delete);

        yourName.setText(String.format("%s님의", name));

        imgCancel.setOnClickListener(view -> finish());
        btnDelete.setOnClickListener(view -> finish());

        RecyclerView recyclerView = findViewById(R.id.recycler_delete_friend);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setHasFixedSize(true);

        deletList = new ArrayList<>();
        deleteFriendAdpater = new DeleteFriendAdpater(this, deletList);
        recyclerView.setAdapter(deleteFriendAdpater);

        helper = new ItemTouchHelper(new ItemTouchHelperCallback(deleteFriendAdpater));
        helper.attachToRecyclerView(recyclerView);


    }
}
