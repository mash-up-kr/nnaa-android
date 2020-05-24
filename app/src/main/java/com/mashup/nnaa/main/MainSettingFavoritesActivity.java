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
import com.mashup.nnaa.select.SetTypeOfFriendActivity;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.BookmarkAdapter;
import com.mashup.nnaa.util.FavoritesAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainSettingFavoritesActivity extends AppCompatActivity {

    private ImageView cancel;
    private TextView txt_manage_favorites;
    private Button btn_next;
    private RecyclerView favorites_recycler;
    private MainSettingFavoritesAdapter adapter;
    private ArrayList<NewQuestionDto> fList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_setting_favorites);

        txt_manage_favorites = findViewById(R.id.txt_manage_favorites);
        cancel = findViewById(R.id.btn_cancel);
        btn_next = findViewById(R.id.btn_next);

        txt_manage_favorites.setText(R.string.setting_manage_favorites);

        cancel.setOnClickListener(view -> {
            finish();
        });

        btn_next.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SetTypeOfFriendActivity.class);
            startActivity(intent);
        });

        favorites_recycler = findViewById(R.id.recycler_main_setting_favorites);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        favorites_recycler.setLayoutManager(linearLayoutManager);

        fList = new ArrayList<>();

        adapter = new MainSettingFavoritesAdapter(this, fList);
        favorites_recycler.setAdapter(adapter);
        favorites_recycler.setHasFixedSize(true);

        this.showFavorites();

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
                    adapter.setFavoritList(fList);
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
