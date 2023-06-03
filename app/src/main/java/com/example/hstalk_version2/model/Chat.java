package com.example.hstalk_version2.model;

import java.util.HashMap;
import java.util.Map;

public class Chat {
    private String id;
    private String name,noidung;

    public Chat() {
    }

    public Chat(String id, String name, String noidung) {
        this.id = id;
        this.name = name;
        this.noidung = noidung;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
    public Map ToMap(){
        Map chat = new HashMap<String, Object>();
        chat.put("id",this.id);
        chat.put("name",this.name);
        chat.put("noidung",this.noidung);
        return chat;
    }
}
