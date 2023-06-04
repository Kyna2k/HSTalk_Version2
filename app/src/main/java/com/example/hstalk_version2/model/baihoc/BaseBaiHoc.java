package com.example.hstalk_version2.model.baihoc;

import java.io.Serializable;
import java.util.ArrayList;

public class BaseBaiHoc implements Serializable {
    private ArrayList<BaiHoc> result;

    public BaseBaiHoc() {
    }

    public BaseBaiHoc(ArrayList<BaiHoc> result) {
        this.result = result;
    }

    public ArrayList<BaiHoc> getResult() {
        return result;
    }

    public void setResult(ArrayList<BaiHoc> result) {
        this.result = result;
    }
}
