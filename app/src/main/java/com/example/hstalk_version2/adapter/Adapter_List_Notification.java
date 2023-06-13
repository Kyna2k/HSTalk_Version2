package com.example.hstalk_version2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hstalk_version2.BR;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ItemNotifiBinding;
import com.example.hstalk_version2.model.notification.Notification;
import com.example.hstalk_version2.ultis.UI_Feature;

import java.util.ArrayList;

public class Adapter_List_Notification extends RecyclerView.Adapter<Adapter_List_Notification.ViewHolder>{

    private Context context;
    private ArrayList<Notification> ds;

    public Adapter_List_Notification(Context context, ArrayList<Notification> ds) {
        this.context = context;
        this.ds = ds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotifiBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_notifi, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ds.get(position));
        holder.binding.time.setText(UI_Feature.getTime(ds.get(position).getThoigian()));
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemNotifiBinding binding;
        public ViewHolder(@NonNull ItemNotifiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Object obj) {
            binding.setVariable(BR.model, obj);
            binding.executePendingBindings();
        }
    }
}
