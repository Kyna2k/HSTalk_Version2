package com.example.hstalk_version2.model.user;

import java.io.Serializable;

public class BaseUser implements Serializable {
    private String mess;
    private User result;

    public BaseUser() {
    }

    public BaseUser(String mess, User result) {
        this.mess = mess;
        this.result = result;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public User getResult() {
        return result;
    }

    public void setResult(User result) {
        this.result = result;
    }
}
