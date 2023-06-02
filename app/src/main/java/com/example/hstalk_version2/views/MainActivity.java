package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.adapter.MainViewPagerAdapater;
import com.example.hstalk_version2.databinding.ActivityMainBinding;
import com.example.hstalk_version2.token.GenAccessToken;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.stringee.StringeeClient;
import com.stringee.call.StringeeCall;
import com.stringee.call.StringeeCall2;
import com.stringee.exception.StringeeError;
import com.stringee.listener.StringeeConnectionListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.NonNull;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainViewPagerAdapater adapater;
    private BottomNavigationView menu;
    public static Map<String, StringeeCall2> callMap = new HashMap<>();
    public static StringeeClient stringeeClient;
    String token = "";
    String token_test = "eyJjdHkiOiJzdHJpbmdlZS1hcGk7dj0xIiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJqdGkiOiJTSy4wLnB5S293cEhOZEQxczJVcjRTcFFGRUFGQVB5M2hHNzFrLTE2ODU3Mzk2NjEiLCJpc3MiOiJTSy4wLnB5S293cEhOZEQxczJVcjRTcFFGRUFGQVB5M2hHNzFrIiwiZXhwIjoxNjg4MzMxNjYxLCJ1c2VySWQiOiJodXkifQ.zMT_Rvlfklz9encgMI5MgR-6QQbdhdDgrNg24ut6uGc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottommenu.setItemIconTintList(null);
        String _id = getSharedPreferences("HocVien",MODE_PRIVATE).getString("_id","");
        if(!_id.equals("") && _id != null)
        {
            token = GenAccessToken.genAccesToken(_id);

        }
        menu = binding.bottommenu;
        adapater = new MainViewPagerAdapater(this);
        binding.mainLayout.setAdapter(adapater);
        binding.mainLayout.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position)
                {
                    case 0:
                        menu.setSelectedItemId(R.id.KhoaHoc);
                        break;
                    case 1:
                        menu.setSelectedItemId(R.id.Hotro);
                        break;
                    case 2:
                        menu.setSelectedItemId(R.id.MangXaHoi);
                        break;
                    case 3:
                        menu.setSelectedItemId(R.id.ThongBao);
                        break;
                    case 4:
                        menu.setSelectedItemId(R.id.HoSo);
                        break;
                }
            }
        });
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.KhoaHoc:
                        binding.mainLayout.setCurrentItem(0);
                        break;
                    case R.id.Hotro:
                        binding.mainLayout.setCurrentItem(1);
                        break;
                    case R.id.MangXaHoi:
                        binding.mainLayout.setCurrentItem(2);
                        break;
                    case R.id.ThongBao:
                        binding.mainLayout.setCurrentItem(3);
                        break;
                    case R.id.HoSo:
                        binding.mainLayout.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
        dangkyquyen();
        inCall();
    }
    private void inCall()
    {
        stringeeClient = new StringeeClient(this);
        stringeeClient.setConnectionListener(new StringeeConnectionListener() {
            @Override
            public void onConnectionConnected(StringeeClient stringeeClient, boolean b) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("check_call", "onConnectionConnected: " + "da connec" );

                    }
                });
            }

            @Override
            public void onConnectionDisconnected(StringeeClient stringeeClient, boolean b) {
                Log.i("check_call", "Diss : ");
            }

            @Override
            public void onIncomingCall(StringeeCall stringeeCall) {
                Log.i("check_call", "Diss : " +  stringeeCall);
            }

            @Override
            public void onIncomingCall2(StringeeCall2 stringeeCall2) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callMap.put(stringeeCall2.getCallId(),stringeeCall2);
                        Intent intent = new Intent(MainActivity.this, CallInActivity.class);
                        intent.putExtra("call_id",stringeeCall2.getCallId());
                        intent.putExtra("where",true);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onConnectionError(StringeeClient stringeeClient, StringeeError stringeeError) {
                Log.e("teo", "Erro : " + stringeeError);

            }

            @Override
            public void onRequestNewToken(StringeeClient stringeeClient) {
                Log.e("check_call", "onRequestNewToken: " + "da connec" );
            }

            @Override
            public void onCustomMessage(String s, JSONObject jsonObject) {
                Log.e("check_call", "onCustomMessage: " + "da connec" );

            }

            @Override
            public void onTopicMessage(String s, JSONObject jsonObject) {
                Log.e("check_call", "onTopicMessage: " + "da connec" );

            }
        });

        stringeeClient.connect(token_test);
        stringeeClient.getUserId();
        int x = 2;
    }
    private void dangkyquyen()
    {
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        },1);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            }else {
                Toast.makeText(this, "Từ chối quyền", Toast.LENGTH_SHORT).show();
            }

        }

    }
}