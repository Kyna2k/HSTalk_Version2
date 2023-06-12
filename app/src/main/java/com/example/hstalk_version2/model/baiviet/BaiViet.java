package com.example.hstalk_version2.model.baiviet;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.model.comment.Comment;
import com.example.hstalk_version2.model.user.User;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BaiViet implements Serializable {
    private String _id,Noidung,Hinhanh,Mauser,Time;
    private ArrayList<User> user;
    private ArrayList<Comment> comment;
    private User users;
    public BaiViet(String _id, String noidung, String hinhanh, String mauser, String time, ArrayList<User> user, ArrayList<Comment> comment) {
        this._id = _id;
        Noidung = noidung;
        Hinhanh = hinhanh;
        Mauser = mauser;
        Time = time;
        this.user = user;
        this.comment = comment;
        this.users = user.get(0);
    }

    public BaiViet() {
    }


    public ArrayList<User> getUser() {
        return user;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
    }

    public ArrayList<Comment> getComment() {
        return comment;
    }

    public void setComment(ArrayList<Comment> comment) {
        this.comment = comment;
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
