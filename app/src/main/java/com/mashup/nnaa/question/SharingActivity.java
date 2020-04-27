package com.mashup.nnaa.question;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.model.SharingDto;
import com.mashup.nnaa.util.SharingAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SharingActivity extends AppCompatActivity {

    private EditText editSearch;
    private ImageView imgCancel, btnNot;
    private Button btnSend;
    private SharingAdapter adapter;
    private ArrayList<SharingDto> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        editSearch = findViewById(R.id.search_view);
        imgCancel = findViewById(R.id.img_sharing_close);
        btnNot = findViewById(R.id.btn_not);
        btnSend = findViewById(R.id.btn_send);

        RecyclerView sharing_recyclerview = findViewById(R.id.recyclerview_sharing);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        sharing_recyclerview.setLayoutManager(linearLayoutManager);

        adapter = new SharingAdapter(this, list);
        sharing_recyclerview.setAdapter(adapter);

        getData();

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getData() {

        List<String> namelist = Arrays.asList("고승윤", "아무개", "김김김", "토니", "디디", "김김김", "토니", "디디");
        List<String> emaillist = Arrays.asList("aaa@aaa.com", "bbb@bbb.com", "ccc@ccc.com", "ddd@ddd.com", "eee@eee.com","ccc@ccc.com", "ddd@ddd.com", "eee@eee.com");

        for (int i = 0; i < namelist.size(); i++) {
            SharingDto sharingDto = new SharingDto();
            sharingDto.setName(namelist.get(i));
            sharingDto.setEmail(emaillist.get(i));

            adapter.addItem(sharingDto);
        }
        adapter.notifyDataSetChanged();
    }
}
