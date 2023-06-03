package com.example.hstalk_version2.views;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivitySelectLoginBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class SelectLoginActivity extends AppCompatActivity {
    ActivitySelectLoginBinding bind;
    private SignInClient onTapClient;
    private BeginSignInRequest signUpRequest;

    private ActivityResultLauncher<IntentSenderRequest>startActivityForResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bind = ActivitySelectLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        bind.btnMLoginemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectLoginActivity.this,LoginWithMailActivity.class));
            }
        });
        bind.btnMLoginphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectLoginActivity.this,LoginWithPhoneActivity.class));
            }
        });
        bind.btnBottomSign.btnMSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectLoginActivity.this,SignInActivity.class));

            }
        });

        bind.btnGg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SelectLoginActivity.this, "Da nhan", Toast.LENGTH_SHORT).show();
                onTapClient.beginSignIn(signUpRequest)
                        .addOnSuccessListener(SelectLoginActivity.this, new OnSuccessListener<BeginSignInResult>() {
                            @Override
                            public void onSuccess(BeginSignInResult beginSignInResult) {
                                try{
                                    IntentSenderRequest intentSenderRequest=new IntentSenderRequest.Builder(
                                           beginSignInResult.getPendingIntent().getIntentSender()).build();
                                    startActivityForResult.launch(intentSenderRequest);
                                }catch (Exception e){
                                    Log.e(TAG,"OnTap don't Run: "+e.getLocalizedMessage());
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

        onTapClient= Identity.getSignInClient(this);
        signUpRequest= BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions
                        .builder().setSupported(true).setServerClientId(getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(false).build())
                .build();
        startActivityForResult=registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode()== Activity.RESULT_OK){
                            try {
                                SignInCredential credential=onTapClient.getSignInCredentialFromIntent(result.getData());
                                String idToken=credential.getGoogleIdToken();
                                if (idToken!=null){
                                    String Email=credential.getId();
                                    String Tenhocvien=credential.getDisplayName();
                                    //Toast.makeText(SelectLoginActivity.this, "Email: "+email+"\n"+"Name: "+name, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SelectLoginActivity.this,MainActivity.class));
                                }
                            }catch (Exception e){
                                Log.e(TAG,"onFailure: ",e);
                            }
                        }else{

                        }
                    }
                }
        );
    }
}