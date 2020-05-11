package com.mashup.nnaa.main.setting;

import androidx.appcompat.app.AppCompatActivity;

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

public class HowUseActivity extends AppCompatActivity {

    private PlayerView exo;
    private ImageView img_close, img_pause;
    private Button btn_start;
    SimpleExoPlayer player;
    Uri videoUri = Uri.parse("android.resource://"+ "com.mashup.nnaa.main.setting" + "/"+ R.raw.description);
  //  Uri videoUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_use);



        exo = findViewById(R.id.exo);
        img_close = findViewById(R.id.img_change_close);
        img_pause = findViewById(R.id.btn_cancel);
        btn_start = findViewById(R.id.btn_start);

        Log.d("PACKAGE", "p" + getPackageName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(this);

        try {
            rawResourceDataSource.open(new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.description)));
        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }
        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        exo.setPlayer(player);
        DataSource.Factory factory = new DefaultDataSourceFactory(this, "Ex89VideoAndExoPlayer");
        ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(factory).createMediaSource(rawResourceDataSource.getUri());

        player.prepare(mediaSource);

        player.setPlayWhenReady(true);
    }

    @Override
    protected void onStop() {
        super.onStop();

        exo.setPlayer(null);
        player.release();
        player = null;
    }
}