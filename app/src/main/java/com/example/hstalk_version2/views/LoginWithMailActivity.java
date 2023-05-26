package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivityLoginWithMailBinding;

public class LoginWithMailActivity extends AppCompatActivity {
    ActivityLoginWithMailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginWithMailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    Log.i("CHECKS", "onFocusChange: ");
                    binding.edtUsername.setStartIconDrawable(null);
                }else {
                    if(!binding.username.getText().toString().equals("")) return;
                    binding.edtUsername.setStartIconDrawable(R.drawable.rezide_icon_human);
                }
            }
        });
        binding.password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    Log.i("CHECKS", "onFocusChange: ");
                    binding.edtPass.setStartIconDrawable(null);
                }else {
                    if(!binding.password.getText().toString().equals("")) return;
                    binding.edtPass.setStartIconDrawable(R.drawable.rezide_icon_lock);
                }
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}