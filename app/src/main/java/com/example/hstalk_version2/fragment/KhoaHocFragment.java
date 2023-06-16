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
import com.example.hstalk_version2.adapter.Adapter_List_CapHoc;
import com.example.hstalk_version2.databinding.FragmentCallBinding;
import com.example.hstalk_version2.databinding.FragmentKhoahocBinding;
import com.example.hstalk_version2.model.khoahoc.BaseKhoaHoc;
import com.example.hstalk_version2.model.khoahoc.ResultKhoaHoc;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.ultis.Loading;
import com.example.hstalk_version2.views.CheckOTPActivity;
import com.example.hstalk_version2.views.SignIn2Activity;
import com.example.hstalk_version2.views.TokTokActivity;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class KhoaHocFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    FragmentKhoahocBinding binding;
    Adapter_List_CapHoc adapter_list_capHoc;
    Loading loading;
    User user;
    API api;
    public void KhoaHocFragment()
    {

    }
    public static KhoaHocFragment getInstance()
    {
        KhoaHocFragment KhoaHocFragment = new KhoaHocFragment();
        Bundle bundle = new Bundle();
        KhoaHocFragment.setArguments(bundle);
        return KhoaHocFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentKhoahocBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        api = new API();
        loading = new Loading();
        binding.name.setText( "Hi " +  getActivity().getSharedPreferences("HocVien",MODE_PRIVATE).getString("name","LAN ANH") + ", Wellcome");
        String avatar = getActivity().getSharedPreferences("HocVien",MODE_PRIVATE).getString("avatar", "");
        Glide.with(getActivity()).load(avatar.equals("")? R.drawable.avatar_df:avatar).circleCrop().into(binding.avatar);
        loading.LoadingShow(getContext(),"Đang tải dự liệu");
        getDS();
        binding.reload.setOnRefreshListener(this);
        binding.btnMToktok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TokTokActivity.class));
            }
        });


    }
    private void getDS(){
        binding.reload.setRefreshing(true);
        User _user = new User();
        _user.set_id(getActivity().getSharedPreferences("HocVien",MODE_PRIVATE).getString("_id","LAN ANH"));
        new CompositeDisposable().add(api.getAPI().danhsachcaphoc(_user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::checkotpOk, this::checkotpNo)
        );
    }

    private void checkotpNo(Throwable throwable) {
        loading.LoadingDismi();
        Log.e("TAG", "loidangnhap: ",throwable );
        binding.reload.setRefreshing(false);
    }

    private void checkotpOk(ResultKhoaHoc resultKhoaHoc) {
        if(resultKhoaHoc.getResult() != null)
        {
            getData(resultKhoaHoc.getResult());
        }else {
        }
        loading.LoadingDismi();
        binding.reload.setRefreshing(false);

    }
    private void getData(ArrayList<BaseKhoaHoc> baseKhoaHoc)
    {
        adapter_list_capHoc = new Adapter_List_CapHoc(baseKhoaHoc,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.listCaphoc.setLayoutManager(linearLayoutManager);
        binding.listCaphoc.setAdapter(adapter_list_capHoc);
    }

    @Override
    public void onRefresh() {
        getDS();
    }
}
