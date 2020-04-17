package com.mashup.nnaa.question;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.network.model.bookmarkQuestionDto;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.FavoritesAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends AppCompatActivity {

    private FavoritesAdapter favoritesAdapter;
    private List<bookmarkQuestionDto> fList;
    Button btn_favorites;
    ImageButton imgbtn_past, imgbtn_cancel;
    EditText edit_custom;
    ImageView img_favorites, img_recycler;
    TextView txt_favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        btn_favorites = findViewById(R.id.btn_favorites);
        imgbtn_past = findViewById(R.id.imgbtn_past);
        imgbtn_cancel = findViewById(R.id.imgbtn_cancel);
        edit_custom = findViewById(R.id.edit_custom);
        img_favorites = findViewById(R.id.img_favorites);
        txt_favorites = findViewById(R.id.txt_favorites);
        img_recycler = findViewById(R.id.img_recycler);

        Intent typeintent = getIntent();
        String category = typeintent.getStringExtra("category");
        String type = typeintent.getStringExtra("type");
        String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
        String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();
        String name = typeintent.getStringExtra("name");

        imgbtn_past.setOnClickListener(view -> {
            finish();
        });

        imgbtn_cancel.setOnClickListener(view -> {
            finish();
        });


        edit_custom.setOnClickListener(view -> {
            Intent edit_intent = new Intent(FavoritesActivity.this, MakeQuestionActivity.class);
            edit_intent.putExtra("type", type);
            edit_intent.putExtra("category", category);
            edit_intent.putExtra("name", name);

            startActivity(edit_intent);
        });


        RecyclerView favorites_recycler = findViewById(R.id.favorites_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        favorites_recycler.setLayoutManager(linearLayoutManager);

        fList = new ArrayList<>();
        favoritesAdapter = new FavoritesAdapter(this, fList);
        favorites_recycler.setAdapter(favoritesAdapter);
        favorites_recycler.setHasFixedSize(true);

        this.showFavorites();


    }

    private void showFavorites() {
        Intent intent = getIntent();
        String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
        String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();
        String category = intent.getStringExtra("category");

        RetrofitHelper.getInstance().showFavorites(id, token, new Callback<List<bookmarkQuestionDto>>() {
            @Override
            public void onResponse(Call<List<bookmarkQuestionDto>> call, Response<List<bookmarkQuestionDto>> response) {
                if (response.isSuccessful()) {
                    fList = response.body();
                    Log.v("즐겨찾기 api", "Response =  " + response.code() + "," + "id: " + id + "," + "token: " + token + "," + "category: " + category);
                    favoritesAdapter.setFavoritList(fList);
                } else if (response.code() == 400) {
                    Toast.makeText(FavoritesActivity.this, "즐겨찾기 한 질문이 없습니다.", Toast.LENGTH_SHORT).show();

                } else {
                    Log.v("즐겨찾기 api", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<bookmarkQuestionDto>> call, Throwable t) {
                Log.v("즐겨찾기 api", "에러:" + t.getMessage());
            }
        });
    }
}
