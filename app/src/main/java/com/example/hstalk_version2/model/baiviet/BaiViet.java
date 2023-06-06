package com.example.hstalk_version2.model.baiviet;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.hstalk_version2.R;

import java.io.Serializable;

public class BaiViet implements Serializable {
    private String _id,Noidung,Hinhanh,Mauser,Time;

    public BaiViet() {
    }

    public BaiViet(String _id, String noidung, String hinhanh, String mauser, String time) {
        this._id = _id;
        Noidung = noidung;
        Hinhanh = hinhanh;
        Mauser = mauser;
        Time = time;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNoidung() {
        return Noidung;
    }

    public void setNoidung(String noidung) {
        Noidung = noidung;
    }

    public String getHinhanh() {
        return Hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        Hinhanh = hinhanh;
    }

    public String getMauser() {
        return Mauser;
    }

    public void setMauser(String mauser) {
        Mauser = mauser;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

}
