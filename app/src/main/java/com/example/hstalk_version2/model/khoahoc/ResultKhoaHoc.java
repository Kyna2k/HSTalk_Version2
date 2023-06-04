package com.example.hstalk_version2.model.khoahoc;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultKhoaHoc implements Serializable {
    private ArrayList<BaseKhoaHoc> result;

    public ResultKhoaHoc(ArrayList<BaseKhoaHoc> result) {
        this.result = result;
    }

    public ArrayList<BaseKhoaHoc> getResult() {
        return result;
    }

    public void setResult(ArrayList<BaseKhoaHoc> result) {
        this.result = result;
    }
}
