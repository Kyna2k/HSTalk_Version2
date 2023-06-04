package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.adapter.Adapter_List_BaiHoc;
import com.example.hstalk_version2.databinding.ActivityDanhSachBaiHocBinding;
import com.example.hstalk_version2.model.baihoc.BaiHoc;
import com.example.hstalk_version2.model.baihoc.BaseBaiHoc;
import com.example.hstalk_version2.model.khoahoc.BaseKhoaHoc;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.ultis.Loading;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DanhSachBaiHocActivity extends AppCompatActivity {
    ActivityDanhSachBaiHocBinding binding;
    Loading loading;
    API api;
    BaseKhoaHoc baseKhoaHoc;
    Adapter_List_BaiHoc adapter_list_baiHoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDanhSachBaiHocBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FullView();
        api = new API();
        loading = new Loading();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        baseKhoaHoc = (BaseKhoaHoc) getIntent().getExtras().getSerializable("baihoc");
        loading.LoadingShow(DanhSachBaiHocActivity.this,"Đang tải dữ liệu");
        getDS();
    }
    private void FullView()
    {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(android.R.color.transparent));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.background_color_baihoc));
    }
    private void getDS()
    {

        new CompositeDisposable().add(api.getAPI().danhsachbaihoctheocap(baseKhoaHoc.getCapHoc())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::okay, this::loi)
        );
    }

    private void loi(Throwable throwable)
    {
        loading.LoadingDismi();
        Log.e("TAG", "loidangnhap: ",throwable );
    }

    private void okay(BaseBaiHoc baseBaiHoc) {
        if(baseBaiHoc.getResult() != null)
        {
            getData(baseBaiHoc.getResult());
        }else {

        }
        loading.LoadingDismi();
    }
    private void getData(ArrayList<BaiHoc> ds)
    {
        adapter_list_baiHoc = new Adapter_List_BaiHoc(ds,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.listbaihoc.setLayoutManager(linearLayoutManager);
        binding.listbaihoc.setAdapter(adapter_list_baiHoc);
    }
}