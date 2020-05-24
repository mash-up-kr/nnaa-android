package com.mashup.nnaa.main.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.mashup.nnaa.R;
import com.mashup.nnaa.main.MainActivity;
import com.mashup.nnaa.select.SetTypeOfFriendActivity;

public class HowUseActivity extends AppCompatActivity {

    private ImageView img_close, img_pause;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_use);

        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?time_continue=9&v=o0XffbT5V54&feature=emb_logo"));

        if (intent1.resolveActivity(getPackageManager()) != null) {
            startActivity(intent1);
        }
        img_close = findViewById(R.id.img_change_close);
        img_pause = findViewById(R.id.btn_cancel);
        btn_start = findViewById(R.id.btn_next);

        img_pause.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });
        img_close.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });
        btn_start.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SetTypeOfFriendActivity.class);
            startActivity(intent);
        });
    }
}