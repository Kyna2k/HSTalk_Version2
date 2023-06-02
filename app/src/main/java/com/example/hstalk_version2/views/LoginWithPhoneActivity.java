package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivityLoginWithPhoneBinding;
import com.example.hstalk_version2.model.user.User;

public class LoginWithPhoneActivity extends AppCompatActivity {
    ActivityLoginWithPhoneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginWithPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    Log.i("CHECKS", "onFocusChange: ");
                    binding.edtPhone.setStartIconDrawable(null);
                }else {
                    if(!binding.phone.getText().toString().equals("")) return;
                    binding.edtPhone.setStartIconDrawable(R.drawable.rezide_icon_phone2);
                }
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.btnLayotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.phone.getText().toString().equals("")){
                    Intent intent =  new Intent(LoginWithPhoneActivity.this, CheckOTPActivity.class);
                    User user = new User();
                    user.setSdt(binding.phone.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",user);
                    //ACtion 0 dang nhap
                    bundle.putInt("ACTION",0);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginWithPhoneActivity.this, "Vui long khong bo trong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}