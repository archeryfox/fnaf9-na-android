package com.example.dogram;

public class User {
    public User() {
    }


    public User(String name, String password, String avatar) {
        this.name = name;
        this.password = password;
        this.avatar = avatar;
    }

    public User(String name, String password, String avatar, boolean isCurrent) {
        this.name = name;
        this.password = password;
        this.avatar = avatar;
        this.isCurrent = isCurrent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean getIsCurrent() {
        return this.isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    private String name;
    private String password;
    private String avatar;
    private boolean isCurrent;
}
