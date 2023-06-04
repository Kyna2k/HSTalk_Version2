package com.example.hstalk_version2.model.baihoc;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class BaiHoc implements Serializable {
    private String _id,Tenbaihoc,Video,Baitap,Hinhanh,Macaphoc;

    public BaiHoc() {
    }

    public BaiHoc(String _id, String tenbaihoc, String video, String baitap, String hinhanh, String macaphoc) {
        this._id = _id;
        Tenbaihoc = tenbaihoc;
        Video = video;
        Baitap = baitap;
        Hinhanh = hinhanh;
        Macaphoc = macaphoc;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenbaihoc() {
        return Tenbaihoc;
    }

    public void setTenbaihoc(String tenbaihoc) {
        Tenbaihoc = tenbaihoc;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getBaitap() {
        return Baitap;
    }

    public void setBaitap(String baitap) {
        Baitap = baitap;
    }

    public String getHinhanh() {
        return Hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        Hinhanh = hinhanh;
    }

    public String getMacaphoc() {
        return Macaphoc;
    }

    public void setMacaphoc(String macaphoc) {
        Macaphoc = macaphoc;
    }
    @BindingAdapter("urlbackground")
    public static void loadImage(ImageView imageView, String avt) {
        if(avt != null && !avt.equals(""))
        {
//           Cái ngành l này địt mẹ nó , bố mày mà lấy vợ giàu có cái l ngành
            Glide.with(imageView.getContext()).load(avt).centerCrop()
                    .into(imageView);
        }

    }
}
