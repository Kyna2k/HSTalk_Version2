package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivityCheckOtpactivityBinding;

public class CheckOTPActivity extends AppCompatActivity {
    ActivityCheckOtpactivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.otp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    Log.i("CHECKS", "onFocusChange: ");
                    binding.edtOtp.setStartIconDrawable(null);
                }else {
                    if(!binding.otp.getText().toString().equals("")) return;
                    binding.edtOtp.setStartIconDrawable(R.drawable.rezide_icon_phone2);
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