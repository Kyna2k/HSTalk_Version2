package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivitySignIn2Binding;
import com.example.hstalk_version2.ultis.UI_Feature;

public class SignIn2Activity extends AppCompatActivity {
    ActivitySignIn2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignIn2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.passInSignin.setOnFocusChangeListener(onFocusChangeListener);
        binding.repassInSignin.setOnFocusChangeListener(onFocusChangeListener);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            switch (view.getId()){
                case R.id.pass_in_signin:
                    UI_Feature.show_hide_icon_edit(b,binding.edtPassInSignin,binding.passInSignin,R.drawable.rezide_icon_lock);
                    break;
                case R.id.repass_in_signin:
                    UI_Feature.show_hide_icon_edit(b,binding.edtRepassInSignin,binding.repassInSignin,R.drawable.rezide_icon_lock);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }
        }
    };
}