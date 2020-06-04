package com.mashup.nnaa.question;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.Questionnaire;
import com.mashup.nnaa.util.SharingAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharingActivity extends AppCompatActivity {

    private EditText editSearch;
    private ImageView imgCancel;
    private SharingAdapter adapter;
    private ArrayList<Questionnaire> list;
    private String TAG = "SharingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        editSearch = findViewById(R.id.search_view);
        imgCancel = findViewById(R.id.img_sharing_close);

        Intent intent = getIntent();

        String cateogry = intent.getStringExtra("category");
        String json = intent.getStringExtra("list");
        Intent intent1 = new Intent(SharingActivity.this, SharingAdapter.class);
        intent1.putExtra("category", cateogry);
        intent1.putExtra("list", json);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        imgCancel.setOnClickListener(view -> finish());

        RecyclerView sharing_recyclerview = findViewById(R.id.recyclerview_sharing);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        sharing_recyclerview.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();

        adapter = new SharingAdapter(this, list);
        sharing_recyclerview.setAdapter(adapter);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                RetrofitHelper.getInstance().userName(charSequence.toString(), new Callback<ArrayList<Questionnaire>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Questionnaire>> call, Response<ArrayList<Questionnaire>> response) {
                        list = response.body();
                        adapter.setSharinglist(list);
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Questionnaire>> call, Throwable t) {
                        Log.v(TAG, t.getMessage());
                    }
                });
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
