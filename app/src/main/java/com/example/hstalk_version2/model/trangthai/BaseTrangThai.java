package com.example.hstalk_version2.model.trangthai;

public class BaseTrangThai {
    private TrangThaiRe result;
    private String mess;

    public BaseTrangThai(TrangThaiRe result, String mess) {
        this.result = result;
        this.mess = mess;
    }

    public TrangThaiRe getResult() {
        return result;
    }

    public void setResult(TrangThaiRe result) {
        this.result = result;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
