package com.example.hstalk_version2.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.FragmentCallBinding;
import com.example.hstalk_version2.databinding.FragmentKhoahocBinding;

public class KhoaHocFragment extends Fragment {
    FragmentKhoahocBinding binding;
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
        binding.name.setText( "Hi " +  getActivity().getSharedPreferences("HocVien",MODE_PRIVATE).getString("name","LAN ANH") + ", Wellcome");
        String avatar = getActivity().getSharedPreferences("HocVien",MODE_PRIVATE).getString("avatar", "");
        Glide.with(getActivity()).load(avatar.equals("")? R.drawable.avatar_df:avatar).circleCrop().into(binding.avatar);
    }
}
