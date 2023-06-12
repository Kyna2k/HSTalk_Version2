package com.example.hstalk_version2.model.baiviet;

import java.util.ArrayList;

public class ResBaiViet {
    private ArrayList<BaiViet> result;

    public ResBaiViet(ArrayList<BaiViet> result) {
        this.result = result;
    }

    public ArrayList<BaiViet> getResult() {
        return result;
    }

    public void setResult(ArrayList<BaiViet> result) {
        this.result = result;
    }
}
