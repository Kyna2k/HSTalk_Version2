package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.hstalk_version2.R;

public class WellComeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_come);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getSharedPreferences("setting",MODE_PRIVATE).getBoolean("ISLOADING",false))
                {
                    startActivity(new Intent(WellComeActivity.this,MainActivity.class));
                }else {
                    startActivity(new Intent(WellComeActivity.this,SelectLoginActivity.class));
                }
                finish();
            }
        },(long) Math.floor(Math.random()*3000)+1000);
    }
}