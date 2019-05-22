package com.android.atm_dj;

public class userInfo {
    private int id;
    private String username;
    private String paswd;
    private String usermoney;

    public userInfo(int id, String username, String paswd, String usermoney){
        this.id=id;
        this.username=username;
        this.paswd=paswd;
        this.usermoney=usermoney;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPaswd(String paswd) {
        this.paswd = paswd;
    }

    public String getPaswd() {
        return paswd;
    }

    public void setUsermoney(String usermoney) {
        this.usermoney = usermoney;
    }

    public String getUsermoney() {
        return usermoney;
    }

    @Override
    public String toString() {
        return "userInfo{"+"id="+id+",username='"+username+'\''+",paswd='"+paswd+'\''+",money='"+usermoney+'\''+'}';
    }
}
