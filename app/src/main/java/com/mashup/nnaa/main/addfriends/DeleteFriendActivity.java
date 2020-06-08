package com.mashup.nnaa.main.addfriends;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public static final int RESULT_DELETE_OK = 999;

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

        Intent intent = getIntent();
        deletList = new ArrayList<>();
        deletList = (ArrayList<FriendDto>) intent.getSerializableExtra("list");
        deleteFriendAdpater = new DeleteFriendAdpater(this, deletList);
        recyclerView.setAdapter(deleteFriendAdpater);

        helper = new ItemTouchHelper(new ItemTouchHelperCallback(deleteFriendAdpater));
        helper.attachToRecyclerView(recyclerView);

        btnDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("친구 삭제 완료");
            builder.setMessage("친구 삭제를 완료하셨나요?");
            builder.setPositiveButton("네", (dialogInterface, i) -> {
                Toast.makeText(getApplicationContext(), "친구삭제 완료.", Toast.LENGTH_SHORT).show();

                Intent delete_question = new Intent(this, AddFriendActivity.class);

                delete_question.putExtra("delete", deletList);

                setResult(RESULT_DELETE_OK, delete_question);
                finish();
            });
            builder.setNegativeButton("아니요", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "친구 삭제를 완료해주세요~", Toast.LENGTH_SHORT).show());
            builder.show();
        });
    }
}
