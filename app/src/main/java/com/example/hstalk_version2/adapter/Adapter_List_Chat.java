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
import com.example.hstalk_version2.databinding.ItemChatBinding;
import com.example.hstalk_version2.databinding.ItemSupportBinding;
import com.example.hstalk_version2.model.Chat;

import java.util.ArrayList;

public class Adapter_List_Chat extends RecyclerView.Adapter<Adapter_List_Chat.ViewHolder>{
    private Context context;
    private ArrayList<Chat> ds;

    public Adapter_List_Chat(Context context, ArrayList<Chat> ds) {
        this.context = context;
        this.ds = ds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChatBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_chat, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ds.get(position));

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemChatBinding binding;
        public ViewHolder(@NonNull ItemChatBinding _binding) {
            super(_binding.getRoot());
            this.binding = _binding;
        }
        public void bind(Object obj) {
            binding.setVariable(BR.model, obj);
            binding.executePendingBindings();
        }
    }
}
