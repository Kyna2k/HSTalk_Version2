package com.example.hstalk_version2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hstalk_version2.BR;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.databinding.ItemPostsBinding;
import com.example.hstalk_version2.model.baiviet.BaseBaiViet;

import java.util.ArrayList;

public class Adapter_List_BaiViet extends RecyclerView.Adapter<Adapter_List_BaiViet.ViewHolder>{
    private Context context;
    private ArrayList<BaseBaiViet> ds;

    public Adapter_List_BaiViet(Context context, ArrayList<BaseBaiViet> ds) {
        this.context = context;
        this.ds = ds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_posts,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ds.get(position));
        holder.binding.soluongbinhluan.setText(ds.get(position).getSoluongcomment() + " Bình luận");
        holder.binding.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bmap = ((BitmapDrawable)holder.binding.icLike.getDrawable()).getBitmap();
                Drawable unlike =context.getResources().getDrawable(R.mipmap.like);
                Bitmap myLogo = ((BitmapDrawable) unlike).getBitmap();
                if(bmap.sameAs(myLogo))
                {
                    holder.binding.icLike.setImageResource(R.mipmap.like2);
                }else {
                    holder.binding.icLike.setImageResource(R.mipmap.like);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemPostsBinding binding;
        public ViewHolder(@NonNull ItemPostsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Object obj) {
            binding.setVariable(BR.model, obj);
            binding.executePendingBindings();
        }
    }
}
