package com.example.hstalk_version2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hstalk_version2.BR;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ItemCaphocBinding;
import com.example.hstalk_version2.model.khoahoc.BaseKhoaHoc;
import com.example.hstalk_version2.views.DanhSachBaiHocActivity;

import java.util.ArrayList;

public class Adapter_List_CapHoc extends RecyclerView.Adapter<Adapter_List_CapHoc.ViewHolder>{

    private ArrayList<BaseKhoaHoc> ds;
    private Context context;

    public Adapter_List_CapHoc(ArrayList<BaseKhoaHoc> ds, Context context) {
        this.ds = ds;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCaphocBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_caphoc, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ds.get(position));
        holder.binding.tiendo.setText(ds.get(position).getTiendo() + "%" );
        if(ds.get(position).getTiendo() == 0)
        {
            holder.binding.progressBar2.setVisibility(View.INVISIBLE);
            holder.binding.tiendo.setVisibility(View.INVISIBLE);
        }else {
            holder.binding.progressBar2.setProgress(ds.get(position).getTiendo());

        }
        holder.binding.btnCaphoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DanhSachBaiHocActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baihoc",ds.get(holder.getAdapterPosition()));
                intent.putExtras(bundle);
                (context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCaphocBinding binding;
        public ViewHolder(@NonNull ItemCaphocBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Object obj) {
            binding.setVariable(BR.model, obj);
            binding.executePendingBindings();
        }
    }
}
