package com.example.hstalk_version2.model.baiviet;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.model.user.User;

import java.io.Serializable;

public class BaseBaiViet implements Serializable {
    private User user;
    private BaiViet baiviet;
    private int soluongcomment;

    public BaseBaiViet() {
    }

    public BaseBaiViet(User user, BaiViet baiviet, int soluongcomment) {
        this.user = user;
        this.baiviet = baiviet;
        this.soluongcomment = soluongcomment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BaiViet getBaiviet() {
        return baiviet;
    }

    public void setBaiviet(BaiViet baiviet) {
        this.baiviet = baiviet;
    }

    public int getSoluongcomment() {
        return soluongcomment;
    }

    public void setSoluongcomment(int soluongcomment) {
        this.soluongcomment = soluongcomment;
    }
    @BindingAdapter("hinhanh")
    public static void loadImage(ImageView imageView, String avt) {
        if(avt != null && !avt.equals(""))
        {
//           Cái ngành l này địt mẹ nó , bố mày mà lấy vợ giàu có cái l ngành
            Glide.with(imageView.getContext()).load(avt)
                    .placeholder(R.mipmap.ic_loading)
                    .into(imageView);
        }

    }
}
