package com.mashup.nnaa.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.NewQuestionDto;
import com.mashup.nnaa.network.model.bookmarkQuestionDto;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.QuestionAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainSettingFavoritesActivity extends AppCompatActivity {
    private ImageView img_add_msf, img_delete_msf;
    private TextView txt_manage_favorites;
    private Button btn_cancel_msf, btn_next_msf;
    private RecyclerView recyclerQuestion;
    private QuestionAdapter questionAdapter;
    private List<NewQuestionDto> questionList;

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
        });

        img_delete_msf.setOnClickListener(view -> {
            /*즐겨찾기 질문 삭제
            1. 삭제버튼 누르면 질문 스와이프 가능
            -> 삭제할 질문들 스와이프 후 적용 버튼 누르면 삭제된 질문 적용됨?
            2. 체크박스 클릭해서 삭제?
            3. 삭제버튼 안눌러도 원래 화면에서 스와이프 가능한가..?
             */
        });

        btn_cancel_msf.setOnClickListener(view -> {
            Toast.makeText(MainSettingFavoritesActivity.this, "처음으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
            finish();
        });

        btn_next_msf.setOnClickListener(view -> {
            Toast.makeText(MainSettingFavoritesActivity.this, "적용 완료!", Toast.LENGTH_SHORT).show();
            //적용 완료하고 세팅페이지로 넘어가야함
        });

        recyclerQuestion = findViewById(R.id.recycler_main_setting_favorites);
        questionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(this, questionList);
        recyclerQuestion.setAdapter(questionAdapter);
        this.getQuestionRandom();

    }

    private void getQuestionRandom() {
        Intent intent = getIntent();
        String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
        String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();

        RetrofitHelper.getInstance().showFavorites(id, token, new Callback<ArrayList<NewQuestionDto>>() {
            @Override
            public void onResponse(Call<ArrayList<NewQuestionDto>> call, Response<ArrayList<NewQuestionDto>> response) {
                if (questionList != null) {

                } else if (questionList.size() == 0) {
                    Toast.makeText(MainSettingFavoritesActivity.this, "즐겨찾기된 질문이 없습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("QuestionRandom", "No Question");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NewQuestionDto>> call, Throwable t) {
                Log.v("QuestionRandom", "에러:" + t.getMessage());
            }

        });
    }
}
