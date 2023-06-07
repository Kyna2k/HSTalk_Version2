package com.example.hstalk_version2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hstalk_version2.BR;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ItemCommentBinding;
import com.example.hstalk_version2.model.comment.BaseComment;
import com.example.hstalk_version2.ultis.UI_Feature;
import com.example.hstalk_version2.views.BinhLuanActivity;

import java.util.ArrayList;


public class Adapter_List_Comment extends RecyclerView.Adapter<Adapter_List_Comment.ViewHolder>{
    private ArrayList<BaseComment> ds;
    private Context context;

    public Adapter_List_Comment(ArrayList<BaseComment> ds, Context context) {
        this.ds = ds;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_comment,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ds.get(position));
        holder.binding.time.setText(UI_Feature.getTime(ds.get(position).getComment().getNgay()));
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCommentBinding binding;
        public ViewHolder(@NonNull ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Object obj) {
            binding.setVariable(BR.model, obj);
            binding.executePendingBindings();
        }
    }
}
