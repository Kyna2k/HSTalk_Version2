package com.example.hstalk_version2.views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivityDangBaiBinding;
import com.example.hstalk_version2.model.baiviet.BaiViet;
import com.example.hstalk_version2.model.baiviet.ResBaiViet2;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.ultis.Loading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DangBaiActivity extends AppCompatActivity {
    String id;
    ActivityDangBaiBinding binding;
    Loading loading;
    API api;
    File file = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangBaiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = new API();
        loading = new Loading();
        id = getSharedPreferences("HocVien",MODE_PRIVATE).getString("_id", "");
        String avatar = getSharedPreferences("HocVien",MODE_PRIVATE).getString("avatar", "");
        String ten =  getSharedPreferences("HocVien",MODE_PRIVATE).getString("name", "");
        String gioitinh = getSharedPreferences("HocVien",MODE_PRIVATE).getString("gioitinh", "");
        Glide.with(this).load(avatar).into(binding.avatar);
        binding.ten.setText(ten);
        binding.giotinh.setText(gioitinh);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.btnDangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noidung = binding.noidung.getText().toString();
                dangbai(noidung,file);
            }
        });
        binding.btnThemhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog();
            }
        });
    }
    private void Dialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_chonhinh,null);
        builder.setView(view);
        CardView layhinh = view.findViewById(R.id.layhinh);
        CardView chuphinh = view.findViewById(R.id.chuphinh);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        layhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
                alertDialog.dismiss();
            }
        });
        chuphinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHinh.launch(new Intent(DangBaiActivity.this, CameraActivity.class));
                alertDialog.dismiss();
            }
        });
    }
    private void chooseImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("image/*");// if you want to you can use pdf/gif/video
            intent.setAction(Intent.ACTION_GET_CONTENT);
            someActivityResultLauncher.launch(intent);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // There are no request codes
                Intent data = result.getData();
                Uri imagePath = data.getData();
                file = new File(DangBaiActivity.this.getCacheDir(),"hinh.png");
                try {
                    InputStream in = DangBaiActivity.this.getContentResolver().openInputStream(imagePath);
                    OutputStream out = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int len;
                    while((len=in.read(buf))>0){
                        out.write(buf,0,len);
                    }
                    out.close();
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(DangBaiActivity.this).load(file).diskCacheStrategy(DiskCacheStrategy.NONE )
                        .skipMemoryCache(true).into(binding.hinh);
            }
        }
    });
    private ActivityResultLauncher<Intent> getHinh = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            switch (result.getResultCode()) {
                case 555:
                    Intent intent = result.getData();
                    Bundle bundle = intent.getExtras();
                    String x = bundle.getString("data");
                    file = new File(bundle.getString("data"));
                    Glide.with(DangBaiActivity.this).load(file).diskCacheStrategy(DiskCacheStrategy.NONE )
                            .skipMemoryCache(true).into(binding.hinh);
            }
        }
    });

    private void dangbai(String noidung,File file) {
        loading.LoadingShow(this,"Đang tải bài viết");
        RequestBody _noidung = RequestBody.create(MediaType.parse("multipart/form-data"), noidung);
        RequestBody _id = RequestBody.create(MediaType.parse("multipart/form-data"), id);
        MultipartBody.Part multipartBody;
        if(file != null)
        {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            multipartBody = MultipartBody.Part.createFormData("Hinhanh",file.getName(),requestFile);
        }else {
            multipartBody = null;
        }
        new CompositeDisposable().add(api.getAPI().addbaiviet(_id,_noidung,multipartBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::thanhcong, this::thatbai)
        );
    }

    private void thanhcong(ResBaiViet2 baiViet) {
        if(baiViet.getResult() != null)
        {
            finish();

        }
        loading.LoadingDismi();
    }

    private void thatbai(Throwable throwable) {
        Log.d("TAG", "thatbai: ",throwable);
        loading.LoadingDismi();
    }

}