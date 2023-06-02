package com.example.hstalk_version2.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hstalk_version2.adapter.Adapter_List_Call;
import com.example.hstalk_version2.databinding.FragmentCallBinding;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.views.LoginWithMailActivity;
import com.example.hstalk_version2.views.MainActivity;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CallFragment extends Fragment {
    private FragmentCallBinding binding;
    private Adapter_List_Call adapter_list_call;
    API api;
    public void CallFragment()
    {

    }
    public static CallFragment getInstance()
    {
        CallFragment callFragment = new CallFragment();
        Bundle bundle = new Bundle();
        callFragment.setArguments(bundle);
        return callFragment;
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
        binding = FragmentCallBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        api = new API();
        GetList();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    private void getData(ArrayList<User> ds)
    {
        adapter_list_call = new Adapter_List_Call(ds,getActivity());
        binding.listCall.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.listCall.setAdapter(adapter_list_call);
    }
    private void GetList()
    {
        new CompositeDisposable().add(api.getAPI().GetDSHocVien()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::dangnhap, this::loidangnhap)
        );
    }

    private void loidangnhap(Throwable throwable)
    {
        Log.e("TAG", "loidangnhap: ",throwable );
    }

    private void dangnhap(BaseUser baseUser) {
        if(baseUser.getList() != null)
        {
            getData(baseUser.getList());
        }else {
        }
    }
}
