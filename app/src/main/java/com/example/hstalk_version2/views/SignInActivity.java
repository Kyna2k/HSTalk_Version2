package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivitySignInBinding;
import com.example.hstalk_version2.ultis.UI_Feature;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.name.setOnFocusChangeListener(onFocusChangeListener);
        binding.gioitinh.setOnFocusChangeListener(onFocusChangeListener);
        binding.phoneOrEmail.setOnFocusChangeListener(onFocusChangeListener);
        binding.btnBack.setOnClickListener(clickListener);
        binding.btnDangky.setOnClickListener(clickListener);
    }
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btn_dangky:
                    dangky();
                    break;
                case R.id.btn_back:
                    finish();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }
        }
    };
    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {

            switch (view.getId()){
                case R.id.name:
                    UI_Feature.show_hide_icon_edit(b,binding.edtName,binding.name,R.drawable.rezide_icon_human);
                    break;
                case R.id.gioitinh:
                    UI_Feature.show_hide_icon_edit(b,binding.edtGioitinh,binding.gioitinh,R.drawable.rezide_icon_sex);
                    break;
                case R.id.phone_or_email:
                    UI_Feature.show_hide_icon_edit(b,binding.edtPhoneOrEmail,binding.phoneOrEmail,R.drawable.rezide_icon_phone2);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }
        }
    };
    private void dangky()
    {
        if(binding.phoneOrEmail.getText().toString().matches("\\d+")){
            startActivity(new Intent(SignInActivity.this, CheckOTPActivity.class));
        }else {

        }
    }

}