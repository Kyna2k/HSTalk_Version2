package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivityHocBaiBinding;
import com.example.hstalk_version2.ultis.Loading;

public class HocBaiActivity extends AppCompatActivity {
    ActivityHocBaiBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHocBaiBinding.inflate(getLayoutInflater());
        Loading loading = new Loading();
        setContentView(binding.getRoot());
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String video = getIntent().getStringExtra("link");
        binding.VideoView.setVideoURI(Uri.parse(video));
        MediaController controller = new MediaController(this);
        binding.VideoView.setMediaController(controller);
        controller.setAnchorView(binding.VideoView);
        controller.setMediaPlayer(binding.VideoView);
        binding.VideoView.start();
        loading.LoadingShow(this,"Đang tải vidoe");
        binding.VideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                loading.LoadingDismi();
            }
        });
    }

}