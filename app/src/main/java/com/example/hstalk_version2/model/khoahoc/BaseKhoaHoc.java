package com.example.hstalk_version2.model.khoahoc;

import java.io.Serializable;

public class BaseKhoaHoc implements Serializable {
    private CapHoc CapHoc;
    private int tiendo;

    public BaseKhoaHoc() {
    }

    public BaseKhoaHoc(com.example.hstalk_version2.model.khoahoc.CapHoc capHoc, int tiendo) {
        CapHoc = capHoc;
        this.tiendo = tiendo;
    }

    public com.example.hstalk_version2.model.khoahoc.CapHoc getCapHoc() {
        return CapHoc;
    }

    public void setCapHoc(com.example.hstalk_version2.model.khoahoc.CapHoc capHoc) {
        CapHoc = capHoc;
    }

    public int getTiendo() {
        return tiendo;
    }

    public void setTiendo(int tiendo) {
        this.tiendo = tiendo;
    }
}
