package com.example.hstalk_version2.model.khoahoc;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class CapHoc implements Serializable {
    private String _id,Tencaphoc,Hinhanh;

    public CapHoc() {
    }

    public CapHoc(String _id, String tencaphoc, String hinhanh) {
        this._id = _id;
        Tencaphoc = tencaphoc;
        Hinhanh = hinhanh;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTencaphoc() {
        return Tencaphoc;
    }

    public void setTencaphoc(String tencaphoc) {
        Tencaphoc = tencaphoc;
    }

    public String getHinhanh() {
        return Hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        Hinhanh = hinhanh;
    }

}
