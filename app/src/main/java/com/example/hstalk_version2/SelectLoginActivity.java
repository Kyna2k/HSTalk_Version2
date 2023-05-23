package com.example.hstalk_version2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hstalk_version2.databinding.ActivitySelectLoginBinding;

public class SelectLoginActivity extends AppCompatActivity {
    ActivitySelectLoginBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivitySelectLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

    }
}