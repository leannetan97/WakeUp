package com.wakeup.wakeup.ObjectClass;

public class User {

    String phoneNum;

    // Constructor
    public User() {
    }

    public User(String phone) {
        this.phoneNum = phone;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

// check email exists -->signup, checkpw
}
