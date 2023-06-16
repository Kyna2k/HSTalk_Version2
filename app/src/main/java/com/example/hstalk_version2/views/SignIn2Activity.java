package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivitySignIn2Binding;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.ultis.Loading;
import com.example.hstalk_version2.ultis.UI_Feature;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SignIn2Activity extends AppCompatActivity {
    ActivitySignIn2Binding binding;
    Loading loading;
    API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignIn2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = new API();
        loading = new Loading();
        binding.passInSignin.setOnFocusChangeListener(onFocusChangeListener);
        binding.repassInSignin.setOnFocusChangeListener(onFocusChangeListener);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = (User) getIntent().getExtras().getSerializable("user");
                if(!binding.repassInSignin.getText().toString().equals("") && !binding.passInSignin.getText().toString().equals(""))
                {

                    if(binding.passInSignin.getText().toString().equals(binding.repassInSignin.getText().toString()))
                    {
                        loading.LoadingShow(SignIn2Activity.this,"Đang xử lý");
                        user.setPassword(binding.passInSignin.getText().toString());

                        if(getIntent().getExtras().getInt("ACTION") == 2)
                        {
                            resetpass(user);
                        }else {
                            SignIn(user);

                        }
                    }
                }else {
                    Toast.makeText(SignIn2Activity.this, "Vui lòng không để trông thông tin mật khẩu", Toast.LENGTH_SHORT).show();
                }

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
    private void SignIn(User _user){

        new CompositeDisposable().add(api.getAPI().SignIn(_user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::dangnhap, this::loidangnhap)
        );
    }

    private void loidangnhap(Throwable throwable) {
        loading.LoadingDismi();
        Log.e("TAG", "loidangnhap: ",throwable );
    }

    private void dangnhap(BaseUser baseUser) {
        if(baseUser.getResult() != null)
        {
            SharedPreferences sharedPreferences = getSharedPreferences("HocVien",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("_id",baseUser.getResult().get_id());
            editor.putString("name",baseUser.getResult().getTenhocvien());
            editor.apply();
            startActivity(new Intent(SignIn2Activity.this,MainActivity.class));
        }else {
        }
        loading.LoadingDismi();

    }
    private void resetpass(User _user){

        new CompositeDisposable().add(api.getAPI().resetpass(_user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::reset, this::loireset)
        );
    }

    private void loireset(Throwable throwable) {
        loading.LoadingDismi();
        Log.e("TAG", "loidangnhap: ",throwable );
    }

    private void reset(BaseUser baseUser) {
        if(baseUser.getMess() != null && baseUser.getStatus() == 1)
        {
            startActivity(new Intent(SignIn2Activity.this,LoginWithMailActivity.class));
        }else {

        }
        loading.LoadingDismi();

    }
}