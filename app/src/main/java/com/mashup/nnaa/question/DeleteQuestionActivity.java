package com.mashup.nnaa.question;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.Choices;
import com.mashup.nnaa.network.QuestionControllerService;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.network.model.Question;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.DeleteAdapter;
import com.mashup.nnaa.util.ItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteQuestionActivity extends AppCompatActivity {

    private DeleteAdapter deleteAdapter;
    private ItemTouchHelper helper;
    private TextView txt_delete_name, txt_delete_type;
    private Button btn_delete;
    private ImageView btn_delete_cancel;
    private ArrayList<NewQuestionDto> dArrayList;
    public static final int RESULT_DELETE_OK = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_question);

        btn_delete = findViewById(R.id.btn_delete);
        btn_delete_cancel = findViewById(R.id.btn_delete_cancel);
        txt_delete_name = findViewById(R.id.txt_delete_name);
        txt_delete_type = findViewById(R.id.txt_delete_type);

        btn_delete_cancel.setOnClickListener(view -> {
            finish();
        });

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        String type = intent.getStringExtra("type");
        String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
        String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();
        String name = intent.getStringExtra("name");

        txt_delete_type.setText(String.format("%s인 , ", type));
        txt_delete_name.setText(String.format("%s께", name));

        RecyclerView recyclerDelete = findViewById(R.id.recycler_delete);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerDelete.setLayoutManager(linearLayoutManager);

        dArrayList = new ArrayList<>();

        dArrayList = (ArrayList<NewQuestionDto>) intent.getSerializableExtra("list");
        deleteAdapter = new DeleteAdapter(this, dArrayList);
        recyclerDelete.setAdapter(deleteAdapter);
        recyclerDelete.setHasFixedSize(true);
        deleteAdapter.notifyDataSetChanged();

        helper = new ItemTouchHelper(new ItemTouchHelperCallback(deleteAdapter));
        helper.attachToRecyclerView(recyclerDelete);

        btn_delete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("질문 삭제 완료");
            builder.setMessage("질문 삭제를 완료하셨나요?");
            builder.setPositiveButton("네", (dialogInterface, i) -> {
                Toast.makeText(getApplicationContext(), "질문삭제 완료.", Toast.LENGTH_SHORT).show();

                Intent delete_question = new Intent(DeleteQuestionActivity.this, LocalQuestionActivity.class);

                delete_question.putExtra("delete", dArrayList);

                setResult(RESULT_DELETE_OK, delete_question);
                finish();
            });
            builder.setNegativeButton("아니요", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "질문 삭제를 완료해주세요~", Toast.LENGTH_SHORT).show());
            builder.show();
        });
    }
}


