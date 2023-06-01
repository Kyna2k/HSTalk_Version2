package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivityLoginWithMailBinding;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.ultis.Loading;
import com.example.hstalk_version2.ultis.UI_Feature;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginWithMailActivity extends AppCompatActivity {
    ActivityLoginWithMailBinding binding;
    Loading loading;
    API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginWithMailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = new API();
        loading = new Loading();
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
        binding.btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.username.getText().toString();
                String pass = binding.password.getText().toString();
                loading.LoadingShow(LoginWithMailActivity.this,"Đang đăng nhập");
                Login(email,pass);
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
    private void Login(String email, String password)
    {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        new CompositeDisposable().add(api.getAPI().LoginWithMail(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::dangnhap, this::loidangnhap)
        );
    }

    private void loidangnhap(Throwable throwable)
    {
        loading.LoadingDismi();
         Log.e("TAG", "loidangnhap: ",throwable );
    }

    private void dangnhap(BaseUser baseUser) {
        if(baseUser.getResult() != null)
        {
            SharedPreferences sharedPreferences = getSharedPreferences("HocVien",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("_id",baseUser.getResult().get_id());
            editor.apply();
            loading.LoadingDismi();
            startActivity(new Intent(LoginWithMailActivity.this,MainActivity.class));
        }else {
            loading.LoadingDismi();
        }
    }
}