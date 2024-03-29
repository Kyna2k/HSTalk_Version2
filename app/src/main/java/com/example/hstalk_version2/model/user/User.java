package com.example.hstalk_version2.model.user;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.hstalk_version2.R;

import java.io.Serializable;

public class User implements Serializable {
    private String _id,Tenhocvien,Gioitinh,Sdt,Email,Password,Avt,TrangThai,Type,Facebook,Google,Otp;

    public User(String _id, String tenhocvien, String gioitinh, String sdt, String email, String password, String avt, String trangThai, String type, String facebook, String google, String otp) {
        this._id = _id;
        Tenhocvien = tenhocvien;
        Gioitinh = gioitinh;
        Sdt = sdt;
        Email = email;
        Password = password;
        Avt = avt;
        TrangThai = trangThai;
        Type = type;
        Facebook = facebook;
        Google = google;
        Otp = otp;
    }

    public String getFacebook() {
        return Facebook;
    }

    public void setFacebook(String facebook) {
        Facebook = facebook;
    }

    public String getGoogle() {
        return Google;
    }

    public void setGoogle(String google) {
        Google = google;
    }

    public String getOtp() {
        return Otp;
    }

    public void setOtp(String otp) {
        Otp = otp;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenhocvien() {
        return Tenhocvien;
    }

    public void setTenhocvien(String tenhocvien) {
        Tenhocvien = tenhocvien;
    }

    public String getGioitinh() {
        return Gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        Gioitinh = gioitinh;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAvt() {
        return Avt;
    }

    public void setAvt(String avt) {
        Avt = avt;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public User() {
    }
    @BindingAdapter("avatar")
    public static void loadImage(ImageView imageView, String avt) {
        if(avt != null && !avt.equals(""))
        {
//           Cái ngành l này địt mẹ nó , bố mày mà lấy vợ giàu có cái l ngành
            Glide.with(imageView.getContext()).load(avt).centerCrop().circleCrop()
                    .placeholder(R.drawable.avatar_df)
                    .into(imageView);
        }

    }
}
