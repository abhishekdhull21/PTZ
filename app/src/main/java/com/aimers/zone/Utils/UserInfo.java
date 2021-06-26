package com.aimers.zone.Utils;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String name,mobile,token;

    public UserInfo() {
    }

    public UserInfo(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
