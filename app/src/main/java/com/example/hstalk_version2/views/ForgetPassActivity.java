package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivityForgetPassBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity {
    ActivityForgetPassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPassBinding.inflate(getLayoutInflater());
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
                    binding.edtUsername.setStartIconDrawable(R.drawable.rezide_icon_phone2);
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