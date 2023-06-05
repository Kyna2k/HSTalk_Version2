package com.example.hstalk_version2.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class BaiVietFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public void BaiVietFragment()
    {

    }
    public static BaiVietFragment getInstance()
    {
        BaiVietFragment callFragment = new BaiVietFragment();
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



    @Override
    public void onRefresh() {

    }
}
