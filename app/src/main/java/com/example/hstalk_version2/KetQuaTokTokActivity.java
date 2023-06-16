package com.example.hstalk_version2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hstalk_version2.views.TokTokActivity;

public class KetQuaTokTokActivity extends AppCompatActivity {
    ImageView hinh1,hinh2,hinh3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua_tok_tok);
        hinh1 = findViewById(R.id.hinh1);
        hinh2 = findViewById(R.id.hinh2);
        hinh3 = findViewById(R.id.hinh3);
        String url1 = getIntent().getExtras().getString("hinh1");
        String url2 = getIntent().getExtras().getString("hinh2");
        String url3 = getIntent().getExtras().getString("hinh3");
        Glide.with(KetQuaTokTokActivity.this).load(url1).diskCacheStrategy(DiskCacheStrategy.NONE )
                .skipMemoryCache(true).into(hinh1);
        Glide.with(KetQuaTokTokActivity.this).load(url2).diskCacheStrategy(DiskCacheStrategy.NONE )
                .skipMemoryCache(true).into(hinh2);
        Glide.with(KetQuaTokTokActivity.this).load(url3).diskCacheStrategy(DiskCacheStrategy.NONE )
                .skipMemoryCache(true).into(hinh3);

    }
}