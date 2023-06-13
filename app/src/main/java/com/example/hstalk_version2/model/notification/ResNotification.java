package com.example.hstalk_version2.model.notification;

import java.io.Serializable;
import java.util.ArrayList;

public class ResNotification implements Serializable {
    private ArrayList<Notification> result;

    public ResNotification() {
    }

    public ResNotification(ArrayList<Notification> result) {
        this.result = result;
    }

    public ArrayList<Notification> getResult() {
        return result;
    }

    public void setResult(ArrayList<Notification> result) {
        this.result = result;
    }
}
