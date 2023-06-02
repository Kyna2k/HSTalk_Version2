package com.example.hstalk_version2.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hstalk_version2.fragment.CallFragment;

public class MainViewPagerAdapater extends FragmentStateAdapter {
    public MainViewPagerAdapater(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new CallFragment().getInstance();
            case 1:
                return new CallFragment().getInstance();
            default:
                return new CallFragment().getInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
