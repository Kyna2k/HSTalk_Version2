package com.example.hstalk_version2.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hstalk_version2.BR;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ItemCaphocBinding;
import com.example.hstalk_version2.databinding.ItemLessonBinding;
import com.example.hstalk_version2.model.baihoc.BaiHoc;
import com.example.hstalk_version2.views.HocBaiActivity;
import com.example.hstalk_version2.views.KiemTraBaiTapActivity;

import java.util.ArrayList;

public class Adapter_List_BaiHoc extends RecyclerView.Adapter<Adapter_List_BaiHoc.ViewHolder>{
    private ArrayList<BaiHoc> ds;
    private Context context;

    public Adapter_List_BaiHoc(ArrayList<BaiHoc> ds, Context context) {
        this.ds = ds;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLessonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_lesson, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ds.get(position));
        holder.binding.playvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HocBaiActivity.class);
                intent.putExtra("link",ds.get(holder.getAdapterPosition()).getVideo());
                (context).startActivity(intent);
            }
        });

        holder.binding.btnKiemtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!ds.get(position).getBaitap().equalsIgnoreCase("Emtry"))
                {
                    Intent intent = new Intent(context, KiemTraBaiTapActivity.class);
                    intent.putExtra("debai",ds.get(holder.getAdapterPosition()).getBaitap());
                    intent.putExtra("caphoc",ds.get(holder.getAdapterPosition()).getMacaphoc());
                    intent.putExtra("mabaihoc",ds.get(holder.getAdapterPosition()).get_id());
                    (context).startActivity(intent);
                }else {
                    diaglog();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }
    private void diaglog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.dialog_empty,null);
        builder.setView(view);
        Button dong = view.findViewById(R.id.okay);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemLessonBinding binding;
        public ViewHolder(@NonNull ItemLessonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Object obj) {
            binding.setVariable(BR.model, obj);
            binding.executePendingBindings();
        }
    }
}
