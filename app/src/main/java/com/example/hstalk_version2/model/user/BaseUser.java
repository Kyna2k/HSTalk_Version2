package com.example.hstalk_version2.model.user;

import java.io.Serializable;
import java.util.ArrayList;

public class BaseUser implements Serializable {
    private String mess;
    private User result;
    private int status;
    private ArrayList<User> list;

    public BaseUser() {
    }

    public BaseUser(String mess, User result) {
        this.mess = mess;
        this.result = result;
    }

    public BaseUser(String mess, User result, int status) {
        this.mess = mess;
        this.result = result;
        this.status = status;
    }

    public BaseUser(String mess, User result, int status, ArrayList<User> list) {
        this.mess = mess;
        this.result = result;
        this.status = status;
        this.list = list;
    }

    public ArrayList<User> getList() {
        return list;
    }

    public void setList(ArrayList<User> list) {
        this.list = list;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
