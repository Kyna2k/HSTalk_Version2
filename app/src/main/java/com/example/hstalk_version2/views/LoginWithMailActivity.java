package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivityLoginWithMailBinding;
import com.example.hstalk_version2.ultis.UI_Feature;

public class LoginWithMailActivity extends AppCompatActivity {
    ActivityLoginWithMailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginWithMailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.username.setOnFocusChangeListener(onFocusChangeListener);
        binding.password.setOnFocusChangeListener(onFocusChangeListener);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.btnMForgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginWithMailActivity.this,ForgetPassActivity.class));
            }
        });
        binding.btnBottomSign.btnMSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginWithMailActivity.this,SignInActivity.class));

            }
        });
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {

            switch (view.getId()){
                case R.id.username:
                    UI_Feature.show_hide_icon_edit(b,binding.edtUsername,binding.username,R.drawable.rezide_icon_human);
                    break;
                case R.id.password:
                    UI_Feature.show_hide_icon_edit(b,binding.edtPass,binding.password,R.drawable.rezide_icon_lock);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }
        }
    };
}