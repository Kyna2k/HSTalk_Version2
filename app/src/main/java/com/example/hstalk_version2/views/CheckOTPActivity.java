package com.example.hstalk_version2.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivityCheckOtpactivityBinding;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.ultis.Loading;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CheckOTPActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    ActivityCheckOtpactivityBinding binding;
    Loading loading;
    User user;
    API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = new API();
        loading = new Loading();
        //Get Phone number
        user = (User) getIntent().getExtras().getSerializable("user");
        //Firebase auth
        mAuth = FirebaseAuth.getInstance();

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
        //Xuly phone

        binding.btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.LoadingShow(CheckOTPActivity.this,"Đang xử lý");
                String code = binding.otp.getText().toString();
                if( getIntent().getExtras().getInt("ACTION") == 2)
                {
                    checkotp(user.getEmail(),binding.otp.getText()
                            .toString());
                }else {
                    setOTP(code);

                }
            }
        });

        //Callback OTP
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                //Khi số điện thoại đăng nhập của bạn nằm trong máy bạn thực hiện getOTP
                //Chúng có thể lập tức lấy mã otp và settext lên ô nhập otp mà không cần phải nhập
                binding.otp.setText(credential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                //Khi fail sẽ chạy vào hàm này
                Log.e("TAG", "onVerificationFailed: ", e);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                //Hàm này sẽ được chạy khi otp gửi thành công, ta sẽ lấy verificationId
                mVerificationId = verificationId;
            }
        };
        if(getIntent().getExtras().getInt("ACTION") != 2)
        {
            getOTP(user.getSdt());
        }


    }
    private void getOTP(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void setOTP(String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            //Đăng nhập thành công lấy dữ liệu user truyền qua để set lên
                            //Ở trang đăng ký hay đăng nhập OTP đều thế, có thông tin gì lấy thông tin đó
                            login(user);
                            startActivity(new Intent(CheckOTPActivity.this,MainActivity.class));
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
    private void login(User _user){

        new CompositeDisposable().add((getIntent().getExtras().getInt("ACTION") == 0 ? api.getAPI().LoginWithPhone(_user) : api.getAPI().SignIn(_user))
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
            editor.putString("avatar",baseUser.getResult().getAvt());
            editor.apply();
            startActivity(new Intent(CheckOTPActivity.this,MainActivity.class));
        }else {
        }
        loading.LoadingDismi();

    }
    private void checkotp(String email, String OTP){
        User _user = new User();
        _user.setEmail(email);
        _user.setOtp(OTP);
        new CompositeDisposable().add(api.getAPI().checkOTP(_user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::checkotpOk, this::checkotpNo)
        );
    }

    private void checkotpNo(Throwable throwable) {
        loading.LoadingDismi();
        Log.e("TAG", "loidangnhap: ",throwable );
    }

    private void checkotpOk(BaseUser baseUser) {
        if(baseUser.getMess() != null && baseUser.getStatus() == 1)
        {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user",user);
            //Action reset pass
            bundle.putInt("ACTION",2);
            Intent intent = new Intent(CheckOTPActivity.this,SignIn2Activity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else {
        }
        loading.LoadingDismi();

    }
}