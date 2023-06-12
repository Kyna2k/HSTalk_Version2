package com.example.hstalk_version2.views;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ActivityEditProfileBinding;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;
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

public class CapNhatThongTinActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    Loading loading;
    File file;
    API api;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = new API();
        loading = new Loading();
        getProfile();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
        binding.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog();
            }
        });

    }
    private void updateProfile()
    {
        loading.LoadingShow(this,"Đang cập nhât");
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), binding.name.getText().toString());
        RequestBody _id = RequestBody.create(MediaType.parse("multipart/form-data"), getSharedPreferences("HocVien",MODE_PRIVATE).getString("_id",""));
        RequestBody gioitinh = RequestBody.create(MediaType.parse("multipart/form-data"), binding.gioitinh.getText().toString());
        RequestBody mota = RequestBody.create(MediaType.parse("multipart/form-data"), binding.mota.getText().toString());
        MultipartBody.Part multipartBody;
        if(file != null)
        {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            multipartBody = MultipartBody.Part.createFormData("Avt",file.getName(),requestFile);
        }else {
            multipartBody = null;
        }
        new CompositeDisposable().add(api.getAPI().capnhatthongtin(_id,name,mota,gioitinh,multipartBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::thanhcong, this::thatbai)
        );


    }

    private void thatbai(Throwable throwable) {
        Log.e("TAG", "lopcapnhat: ",throwable );
        loading.LoadingDismi();
    }

    private void thanhcong(BaseUser baseUser) {
        if(baseUser.getResult() != null)
        {
            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("HocVien",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("_id",baseUser.getResult().get_id());
            editor.putString("name",baseUser.getResult().getTenhocvien());
            editor.putString("avatar",baseUser.getResult().getAvt());
            editor.putString("gioitinh",baseUser.getResult().getGioitinh());
            editor.putString("mota",baseUser.getResult().getMota());
            editor.commit();
            finish();
        }
        loading.LoadingDismi();
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
                getHinh.launch(new Intent(CapNhatThongTinActivity.this, CameraActivity.class));
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
                file = new File(CapNhatThongTinActivity.this.getCacheDir(),"hinh.png");
                try {
                    InputStream in = CapNhatThongTinActivity.this.getContentResolver().openInputStream(imagePath);
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
                Glide.with(CapNhatThongTinActivity.this).load(file).diskCacheStrategy(DiskCacheStrategy.NONE )
                        .skipMemoryCache(true).circleCrop().into(binding.avatar);
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
                    file = new File(bundle.getString("data"));
                    Glide.with(CapNhatThongTinActivity.this).load(file).diskCacheStrategy(DiskCacheStrategy.NONE )
                            .skipMemoryCache(true).circleCrop().into(binding.avatar);
            }
        }
    });
    private void getProfile() {
        User user1 = new User();
        user1.set_id(getSharedPreferences("HocVien",MODE_PRIVATE).getString("_id",""));
        loading.LoadingShow(this,"Đang lấy thông tin");
        new CompositeDisposable().add(api.getAPI().getthongtin(user1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::gethongtin, this::loithongtin)
        );
    }

    private void loithongtin(Throwable throwable) {
        loading.LoadingDismi();
        Log.e("TAG", "loidangnhap: ",throwable );
    }

    private void gethongtin(BaseUser baseUser) {
        if(baseUser.getResult() != null)
        {
            Glide.with(this).load(baseUser.getResult().getAvt() != null && !baseUser.getResult().getAvt().equals("") ? baseUser.getResult().getAvt() : R.drawable.avatar_df).circleCrop().into(binding.avatar);
            binding.mota.setText(baseUser.getResult().getMota());
            binding.gioitinh.setText(baseUser.getResult().getGioitinh());
            binding.name.setText(baseUser.getResult().getTenhocvien());
        }
        loading.LoadingDismi();
    }
}
