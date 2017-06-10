package com.mywebapp.bean;

import java.util.Date;

/**
 * Created by gaorui on 17/5/7.
 */
public class User {
    private int id;
    private String u_name;
    private int u_status;
    private int u_count;

    public int getU_status() {
        return u_status;
    }

    public void setU_status(int u_status) {
        this.u_status = u_status;
    }

    public int getU_count() {
        return u_count;
    }

    public void setU_count(int u_count) {
        this.u_count = u_count;
    }

    public String getU_created_at() {
        return u_created_at;
    }

    public void setU_created_at(String u_created_at) {
        this.u_created_at = u_created_at;
    }

    public String getU_avatar_url() {
        return u_avatar_url;
    }

    public void setU_avatar_url(String u_avatar_url) {
        this.u_avatar_url = u_avatar_url;
    }

    private String u_email;
    private String u_key;
    private String u_nickname;
    private String u_created_at;
    private String u_avatar_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_key() {
        return u_key;
    }

    public void setU_key(String u_key) {
        this.u_key = u_key;
    }

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }


}
