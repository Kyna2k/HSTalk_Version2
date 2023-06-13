package com.example.hstalk_version2.model.notification;

import com.example.hstalk_version2.model.user.User;

import java.io.Serializable;

public class Notification  implements Serializable {
    private String _id,Mauser,noidung,Sender,thoigian;
    private User user;

    public Notification() {
    }

    public Notification(String _id, String mauser, String noidung, String sender, String thoigian, User user) {
        this._id = _id;
        Mauser = mauser;
        this.noidung = noidung;
        Sender = sender;
        this.thoigian = thoigian;
        this.user = user;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMauser() {
        return Mauser;
    }

    public void setMauser(String mauser) {
        Mauser = mauser;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
