package com.example.hstalk_version2.model.trangthai;

import java.io.Serializable;

public class TrangThaiRe implements Serializable {
    private String Mauser,Mabaihoc,Caphoc,_id;
    private int TrangThai,diem;

    public TrangThaiRe() {
    }

    public TrangThaiRe(String mauser, String mabaihoc, String caphoc, String _id, int trangThai, int diem) {
        Mauser = mauser;
        Mabaihoc = mabaihoc;
        Caphoc = caphoc;
        this._id = _id;
        TrangThai = trangThai;
        this.diem = diem;
    }

    public String getMauser() {
        return Mauser;
    }

    public void setMauser(String mauser) {
        Mauser = mauser;
    }

    public String getMabaihoc() {
        return Mabaihoc;
    }

    public void setMabaihoc(String mabaihoc) {
        Mabaihoc = mabaihoc;
    }

    public String getCaphoc() {
        return Caphoc;
    }

    public void setCaphoc(String caphoc) {
        Caphoc = caphoc;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }
}
