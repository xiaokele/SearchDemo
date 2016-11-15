package com.example.administrator.test;

/**
 * Created by Administrator on 2016/11/14.
 */
public class User {
    public String getPrivator() {
        return privator;
    }

    public void setPrivator(String privator) {
        this.privator = privator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;
    public String name;
    public String icon;
    public String privator;
}
