package com.example.hstalk_version2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hstalk_version2.BR;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ItemPostsBinding;
import com.example.hstalk_version2.databinding.ItemSupportBinding;
import com.example.hstalk_version2.handle.Handle_Call;
import com.example.hstalk_version2.model.user.User;

import java.util.ArrayList;

public class Adapter_List_Call extends  RecyclerView.Adapter<Adapter_List_Call.ViewHolder>{
    private ArrayList<User> ds;
    private Context context;
    private Handle_Call call;

    public Adapter_List_Call(ArrayList<User> ds, Context context, Handle_Call call) {
        this.ds = ds;
        this.context = context;
        this.call = call;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSupportBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_support, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ds.get(position));
        holder.binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call.Call(ds.get(holder.getAdapterPosition()).get_id(),ds.get(holder.getAdapterPosition()).getTenhocvien());
            }
        });
        String[] names = ds.get(position).getTenhocvien().split("\\s+");

        holder.binding.ten.setText(names.length == 1 ? names[0] : names[names.length-1]);
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemSupportBinding binding;
        public ViewHolder(ItemSupportBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
        public void bind(Object obj) {
            binding.setVariable(BR.model, obj);
            binding.executePendingBindings();
        }
    }
}
