package com.parentchild.childmonitor;

import android.view.View;
import android.widget.TextView;

public class ChildModel {

    String name, email, pass, uid, parentUid, username;

    ChildModel(){

    }

    public ChildModel(String name, String username, String email, String pass, String parentUid) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.pass = pass;
        this.parentUid = parentUid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
