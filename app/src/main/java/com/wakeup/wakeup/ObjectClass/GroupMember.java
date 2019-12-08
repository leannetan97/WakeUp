package com.wakeup.wakeup.ObjectClass;

public class GroupMember extends User {
    private String userName;
    private boolean isAdmin;
    private String email;
    private String phoneNum;

    public GroupMember(String userName, boolean isAdmin, String email, String phoneNum) {
        this.userName = userName;
        this.isAdmin = isAdmin;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
