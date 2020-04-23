package com.mashup.nnaa.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.NnaaApplication;
import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.BookmarkAdapter;
import com.mashup.nnaa.util.FavoritesAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainSettingFavoritesActivity extends AppCompatActivity {
    private ImageView img_add_msf, img_delete_msf;
    private TextView txt_manage_favorites;
    private Button btn_cancel_msf, btn_next_msf;
    private RecyclerView favorites_recycler;
    private FavoritesAdapter favoritesAdapter;
    private ArrayList<NewQuestionDto> fList;
    private BookmarkAdapter bookmarkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_setting_favorites);

        img_add_msf = findViewById(R.id.img_add_msf);
        img_delete_msf = findViewById(R.id.img_delete_msf);
        txt_manage_favorites = findViewById(R.id.txt_manage_favorites);
        btn_cancel_msf = findViewById(R.id.btn_cancel_msf);
        btn_next_msf = findViewById(R.id.btn_next_msf);

        txt_manage_favorites.setText(R.string.setting_manage_favorites);

        img_add_msf.setOnClickListener(view -> {
            //즐겨찾기 질문 추가
            Intent addintent = new Intent(NnaaApplication.getAppContext(), MainSettingMakeFavoritesActivity.class);
            startActivity(addintent);
        });

        img_delete_msf.setOnClickListener(view -> {

        });

        btn_cancel_msf.setOnClickListener(view -> {
            Toast.makeText(MainSettingFavoritesActivity.this, "처음으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
            finish();
        });

        btn_next_msf.setOnClickListener(view -> {
            Toast.makeText(MainSettingFavoritesActivity.this, "적용 완료!", Toast.LENGTH_SHORT).show();
            //적용 완료하고 세팅페이지로 넘어가야함
        });

        favorites_recycler = findViewById(R.id.recycler_main_setting_favorites);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        favorites_recycler.setLayoutManager(linearLayoutManager);

        fList = new ArrayList<>();
        //favoritesAdapter = new FavoritesAdapter(this, fList);
        //favorites_recycler.setAdapter(favoritesAdapter);

        bookmarkAdapter = new BookmarkAdapter(this,fList);
        favorites_recycler.setAdapter(bookmarkAdapter);
        favorites_recycler.setHasFixedSize(true);

        this.showFavorites();


        /* Swipe to delete
        ItemTouchHelperListener listener = new ItemTouchHelperListener() {
            @Override
            public void onItemSwipe(int position) {

            }
        };
        //ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback(listener);

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper();
        itemTouchhelper.attachToRecyclerView(favorites_recycler);*/


    }

    private void showFavorites() {

        String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
        String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();

        RetrofitHelper.getInstance().showFavorites(id, token, new Callback<ArrayList<NewQuestionDto>>() {
            @Override
            public void onResponse(Call<ArrayList<NewQuestionDto>> call, Response<ArrayList<NewQuestionDto>> response) {
                if (response.isSuccessful()) {
                    fList = response.body();
                    Log.v("즐겨찾기 api", "Response = " + response.code());
                    bookmarkAdapter.setFavoriteList(fList);
                } else if (response.code() == 400) {
                    Toast.makeText(MainSettingFavoritesActivity.this, "즐겨찾기 한 질문이 없습니다.", Toast.LENGTH_SHORT).show();

                } else {
                    Log.v("즐겨찾기 api", response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NewQuestionDto>> call, Throwable t) {
                Log.v("즐겨찾기 api", "에러:" + t.getMessage());
            }
        });
    }

}
