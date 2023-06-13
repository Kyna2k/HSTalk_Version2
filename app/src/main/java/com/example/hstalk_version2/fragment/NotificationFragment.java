package com.example.hstalk_version2.fragment;

import android.content.Context;
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

import com.example.hstalk_version2.adapter.Adapter_List_Notification;
import com.example.hstalk_version2.databinding.FragmentCallBinding;
import com.example.hstalk_version2.databinding.FragmentNotificationBinding;
import com.example.hstalk_version2.model.notification.Notification;
import com.example.hstalk_version2.model.notification.ResNotification;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.ultis.Loading;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener{

    private FragmentNotificationBinding binding;
    API api;
    Loading loading;
    private Adapter_List_Notification adapter_list_notification;
    public NotificationFragment()
    {

    }

    public static NotificationFragment getInstance()
    {
        NotificationFragment notificationFragment = new NotificationFragment();
        Bundle bundle = new Bundle();
        notificationFragment.setArguments(bundle);
        return notificationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater,container,false);
        api = new API();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetList();
    }

    @Override
    public void onRefresh() {
        GetList();
    }
    private void GetList()
    {
        User user = new User();
        user.set_id(getContext().getSharedPreferences("HocVien", Context.MODE_PRIVATE).getString("_id",""));
        binding.reload.setRefreshing(true);
        new CompositeDisposable().add(api.getAPI().danhsachnoti(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::dangnhap, this::loidangnhap)
        );
    }

    private void loidangnhap(Throwable throwable)
    {
        Log.e("TAG", "loidangnhap: ",throwable );
        binding.reload.setRefreshing(false);
    }

    private void dangnhap(ResNotification notification) {
        if(notification.getResult() != null)
        {
            setData(notification.getResult());
        }else {
        }
        binding.reload.setRefreshing(false);
    }
    private void setData(ArrayList<Notification> ds)
    {
        adapter_list_notification = new Adapter_List_Notification(getContext(),ds);
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        l.setOrientation(RecyclerView.VERTICAL);
        binding.listCall.setLayoutManager(l);
        binding.listCall.setAdapter(adapter_list_notification);
    }
}
