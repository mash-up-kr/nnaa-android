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

import com.mashup.nnaa.R;
import com.mashup.nnaa.MakeQuestionActivity;
import com.mashup.nnaa.network.QuestionControllerService;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.UserControllerService;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.network.model.Question;
import com.mashup.nnaa.util.FavoritesAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoritesActivity extends AppCompatActivity {

    private FavoritesAdapter favoritesAdapter;
    private List<NewQuestionDto> fList;
    Button btn_favorites;
    ImageButton imgbtn_past, imgbtn_cancel;
    EditText edit_custom;
    ImageView img_favorites, img_recycler;
    TextView txt_favorites, getType;

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
        getType = findViewById(R.id.get_type);

        Intent typeintent = getIntent();
        if (typeintent != null && typeintent.getExtras() != null) {
            String type = typeintent.getStringExtra("type");
            getType.setText(type);
        }

        imgbtn_past.setOnClickListener(view -> {
            finish();
        });

        imgbtn_cancel.setOnClickListener(view -> {
            finish();
        });


        edit_custom.setOnClickListener(view -> {
            Intent edit_intent = new Intent(FavoritesActivity.this, MakeQuestionActivity.class);
            edit_intent.putExtra("type", String.valueOf(getType));
            startActivity(edit_intent);
        });


        RecyclerView favorites_recycler = findViewById(R.id.favorites_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        favorites_recycler.setLayoutManager(linearLayoutManager);

        fList = new ArrayList<>();
        favoritesAdapter = new FavoritesAdapter(this, fList);
        favorites_recycler.setAdapter(favoritesAdapter);

        Intent retrofit_category = getIntent();
        String category = retrofit_category != null ? retrofit_category.getStringExtra("category") : null;

        this.showFavorites();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://thisisyourbackend.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuestionControllerService service = retrofit.create(QuestionControllerService.class);
        service.getQuestion( category, 30).enqueue(new Callback<List<NewQuestionDto>>() {
            @Override
            public void onResponse(Call<List<NewQuestionDto>> call, Response<List<NewQuestionDto>> response) {
                if (fList != null) {
                    fList = response.body();
                    Log.v("QuestionRandom", "Response =  " + fList + ", " + response.code());
                    favoritesAdapter.setFavoritList(fList);
                } else {
                    Log.v("QuestionRandom", "No Question");
                }
            }

            @Override
            public void onFailure(Call<List<NewQuestionDto>> call, Throwable t) {
                Log.v("QuestionRandom", "에러:" + t.getMessage());
            }
        });

    }

    private void showFavorites() {
       /* RetrofitHelper.getInstance().showFavorites(new Callback<List<NewQuestionDto>>() {
            @Override
            public void onResponse(Call<List<NewQuestionDto>> call, Response<List<NewQuestionDto>> response) {
                if (fList != null) {
                    fList = response.body();
                    Log.v("즐겨찾기 api", "Response =  " + fList + ", " + response.code());
                    favoritesAdapter.setFavoritList(fList);
                } else {
                    Log.v("즐겨찾기 api", "No Question");
                }
            }

            @Override
            public void onFailure(Call<List<NewQuestionDto>> call, Throwable t) {
                Log.v("즐겨찾기 api", "에러:" + t.getMessage());
            }
        });*/

    }
}
