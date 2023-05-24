package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hstalk_version2.databinding.ActivitySelectLoginBinding;

public class SelectLoginActivity extends AppCompatActivity {
    ActivitySelectLoginBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivitySelectLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        bind.btnMLoginemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectLoginActivity.this,LoginWithMailActivity.class));
            }
        });

    }
}