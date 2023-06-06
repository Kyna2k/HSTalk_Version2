package com.example.hstalk_version2.model.baiviet;

import java.util.ArrayList;

public class ResBaiViet {
    private ArrayList<BaseBaiViet> result;

    public ResBaiViet(ArrayList<BaseBaiViet> result) {
        this.result = result;
    }

    public ArrayList<BaseBaiViet> getResult() {
        return result;
    }

    public void setResult(ArrayList<BaseBaiViet> result) {
        this.result = result;
    }
}
