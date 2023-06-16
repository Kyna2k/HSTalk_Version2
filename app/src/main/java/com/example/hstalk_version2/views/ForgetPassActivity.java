package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivityForgetPassBinding;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.ultis.Loading;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ForgetPassActivity extends AppCompatActivity {
    ActivityForgetPassBinding binding;
    Loading loading;
    API api;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = new API();
        loading = new Loading();
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
        binding.btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.username.getText().toString().equals(""))
                {
                    if(validate(binding.username.getText().toString()))
                    {
                        checkemail(binding.username.getText().toString());
                    }else {
                        Toast.makeText(ForgetPassActivity.this, "Email chưa đúng định dạng", Toast.LENGTH_SHORT).show();

                    }

                }else {
                    Toast.makeText(ForgetPassActivity.this, "Vui lòng không để trống email", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public  final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public  boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
    private void checkemail(String email)
    {
        loading.LoadingShow(ForgetPassActivity.this,"Đang gửi yêu cầu");

        user = new User();
        user.setEmail(email);
        new CompositeDisposable().add(api.getAPI().sendOtpCode(user)
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
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        //ACtion 2 reset pass
        bundle.putInt("ACTION",2);
        if(baseUser.getMess() != null && baseUser.getStatus() == 1)
        {
            loading.LoadingDismi();
            Intent intent = new Intent(ForgetPassActivity.this,CheckOTPActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            Toast.makeText(this, baseUser.getMess(), Toast.LENGTH_SHORT).show();
        }else {
            loading.LoadingDismi();
            Toast.makeText(this, baseUser.getMess(), Toast.LENGTH_SHORT).show();
        }
    }
}