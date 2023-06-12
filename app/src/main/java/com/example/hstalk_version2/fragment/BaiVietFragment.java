package com.example.hstalk_version2.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.adapter.Adapter_List_BaiViet;
import com.example.hstalk_version2.databinding.FragmentBaivietBinding;
import com.example.hstalk_version2.model.baiviet.BaiViet;
import com.example.hstalk_version2.model.baiviet.BaseBaiViet;
import com.example.hstalk_version2.model.baiviet.ResBaiViet;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.ultis.Loading;
import com.example.hstalk_version2.views.DangBaiActivity;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BaiVietFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentBaivietBinding binding;
    API api;
    Loading loading;
    private Adapter_List_BaiViet adapter_list_baiViet;
    private int loai;
    public void BaiVietFragment()
    {

    }
    public static BaiVietFragment getInstance(int type)
    {
        BaiVietFragment callFragment = new BaiVietFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        callFragment.setArguments(bundle);
        return callFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            loai = getArguments().getInt("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBaivietBinding.inflate(inflater,container,false);
        loading = new Loading();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String avatar = getActivity().getSharedPreferences("HocVien",MODE_PRIVATE).getString("avatar", "");
        Glide.with(getActivity()).load(avatar.equals("")? R.drawable.avatar_df:avatar).circleCrop().into(binding.avatar);
        api = new API();
        GetList();
        binding.reload.setOnRefreshListener(this);
        binding.btnDangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DangBaiActivity.class));
            }
        });
    }

    @Override
    public void onRefresh() {
        GetList();
    }
    private void GetList()
    {
        binding.reload.setRefreshing(true);
        User user = new User();
        user.set_id(getActivity().getSharedPreferences("HocVien",MODE_PRIVATE).getString("_id",""));
        if(loai == 1)
        {
            new CompositeDisposable().add(api.getAPI().getdanhsachbaiviet()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::dangnhap, this::loidangnhap)
            );
        }else {
            new CompositeDisposable().add(api.getAPI().getdanhsachbaivietcuatoi(user)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::dangnhap, this::loidangnhap)
            );
        }

    }

    private void loidangnhap(Throwable throwable)
    {
        Log.e("TAG", "loidangnhap: ",throwable );
        binding.reload.setRefreshing(false);
    }

    private void dangnhap(ResBaiViet resBaiViet) {
        if(resBaiViet.getResult() != null)
        {
            getData(resBaiViet.getResult());
        }else {
        }
        binding.reload.setRefreshing(false);
    }

    private void getData(ArrayList<BaiViet> list) {
        adapter_list_baiViet = new Adapter_List_BaiViet(getContext(),list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.listBaiviet.setLayoutManager(linearLayoutManager);
        binding.listBaiviet.setAdapter(adapter_list_baiViet);
    }
}
