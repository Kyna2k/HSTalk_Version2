package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivitySignInBinding;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.ultis.Loading;
import com.example.hstalk_version2.ultis.UI_Feature;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    Loading loading;
    API api;
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = new API();
        loading = new Loading();
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
                    loading.LoadingShow(SignInActivity.this,"Đang xử lý");
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
        if(!binding.phoneOrEmail.getText().toString().equals(""))
        {

            if(binding.phoneOrEmail.getText().toString().matches("\\d+"))
            {
                user.setSdt(binding.phoneOrEmail.getText().toString());
            }else {
                user.setEmail(binding.phoneOrEmail.getText().toString());
            }
            signinCheck(user);
        }

    }
    private void signinCheck(User user)
    {
        new CompositeDisposable().add(api.getAPI().SignInCheck(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::yes, this::yesnt)
        );
    }

    private void yes(BaseUser baseUser) {
        if(baseUser.getStatus() == 1)
        {
            user.setGioitinh(binding.gioitinh.getText().toString());
            user.setTenhocvien(binding.name.getText().toString());
            user.setType("HOCVIEN");
            Bundle bundle = new Bundle();
            bundle.putSerializable("user",user);
            if(binding.phoneOrEmail.getText().toString().matches("\\d+")){
                Intent intent = new Intent(SignInActivity.this, CheckOTPActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }else {
                Intent intent = new Intent(SignInActivity.this, SignIn2Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }else {
            Toast.makeText(this, "SDT hoặc Email đã đã được đăng ký", Toast.LENGTH_SHORT).show();
        }
        loading.LoadingDismi();
    }

    private void yesnt(Throwable throwable) {
        loading.LoadingDismi();
        Log.e("TAG", "loidangnhap: ",throwable );
    }

}