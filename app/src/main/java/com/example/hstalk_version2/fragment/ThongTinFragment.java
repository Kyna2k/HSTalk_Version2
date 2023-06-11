package com.example.hstalk_version2.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.adapter.Adapter_List_BaiViet;
import com.example.hstalk_version2.databinding.FragmentThongtinBinding;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.ultis.Loading;
import com.example.hstalk_version2.views.CapNhatThongTinActivity;

public class ThongTinFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private FragmentThongtinBinding binding;
    SharedPreferences sharedPreferences;
    API api;
    Loading loading;
    private Adapter_List_BaiViet adapter_list_baiViet;

    public void ThongTinFragment()
    {

    }
    public static ThongTinFragment getInstance()
    {
        ThongTinFragment callFragment = new ThongTinFragment();
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
        binding = FragmentThongtinBinding.inflate(inflater,container,false);
        sharedPreferences = getActivity().getSharedPreferences("HocVien", Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.baivietcuatoi,new BaiVietFragment().getInstance(0)).commit();
        binding.btnMChinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), CapNhatThongTinActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(getActivity()).load(sharedPreferences.getString("avatar","")).circleCrop().placeholder(R.mipmap.avatar).into(binding.avatar);
        binding.ten.setText(sharedPreferences.getString("name", "@user" +sharedPreferences.getString("_id","")));
        binding.gioitinh.setText(sharedPreferences.getString("gioitinh",""));
        binding.mota.setText(sharedPreferences.getString("mota","Sinh viên trường FPT Polytechnic Thực tập sinh react native - company Atla..."));
    }

    @Override
    public void onRefresh() {

    }
}
