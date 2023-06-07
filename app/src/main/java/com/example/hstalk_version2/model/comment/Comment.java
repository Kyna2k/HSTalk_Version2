package com.example.hstalk_version2.model.comment;

import java.io.Serializable;

public class Comment implements Serializable {
    private String _id,Mauser,Mabaiviet,Noidung,Hinhanh,Ngay;

    public Comment() {
    }

    public Comment(String _id, String mauser, String mabaiviet, String noidung, String hinhanh, String ngay) {
        this._id = _id;
        Mauser = mauser;
        Mabaiviet = mabaiviet;
        Noidung = noidung;
        Hinhanh = hinhanh;
        Ngay = ngay;
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

    public String getMabaiviet() {
        return Mabaiviet;
    }

    public void setMabaiviet(String mabaiviet) {
        Mabaiviet = mabaiviet;
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

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }
}
