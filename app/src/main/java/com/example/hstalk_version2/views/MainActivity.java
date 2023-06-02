package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.adapter.MainViewPagerAdapater;
import com.example.hstalk_version2.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import io.reactivex.annotations.NonNull;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainViewPagerAdapater adapater;
    private BottomNavigationView menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottommenu.setItemIconTintList(null);
        menu = binding.bottommenu;
        adapater = new MainViewPagerAdapater(this);
        binding.mainLayout.setAdapter(adapater);
        binding.mainLayout.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position)
                {
                    case 0:
                        menu.setSelectedItemId(R.id.KhoaHoc);
                        break;
                    case 1:
                        menu.setSelectedItemId(R.id.Hotro);
                        break;
                    case 2:
                        menu.setSelectedItemId(R.id.MangXaHoi);
                        break;
                    case 3:
                        menu.setSelectedItemId(R.id.ThongBao);
                        break;
                    case 4:
                        menu.setSelectedItemId(R.id.HoSo);
                        break;
                }
            }
        });
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.KhoaHoc:
                        binding.mainLayout.setCurrentItem(0);
                        break;
                    case R.id.Hotro:
                        binding.mainLayout.setCurrentItem(1);
                        break;
                    case R.id.MangXaHoi:
                        binding.mainLayout.setCurrentItem(2);
                        break;
                    case R.id.ThongBao:
                        binding.mainLayout.setCurrentItem(3);
                        break;
                    case R.id.HoSo:
                        binding.mainLayout.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
    }

}