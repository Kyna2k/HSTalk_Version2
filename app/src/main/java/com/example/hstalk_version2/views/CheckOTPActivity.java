package com.example.hstalk_version2.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivityCheckOtpactivityBinding;
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

public class CheckOTPActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    ActivityCheckOtpactivityBinding binding;
    String phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Get Phone number
        phonenumber = getIntent().getExtras().getString("number");
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
                String code = binding.otp.getText().toString();
                setOTP(code);
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
        getOTP(phonenumber);

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
                            FirebaseUser user = task.getResult().getUser();
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
}