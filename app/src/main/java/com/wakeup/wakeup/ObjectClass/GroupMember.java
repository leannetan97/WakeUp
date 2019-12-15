package com.wakeup.wakeup.ObjectClass;

public class GroupMember extends User {
    private String userName;
    private boolean isAdmin;
    private String phoneNum;

    public GroupMember(String userName, boolean isAdmin, String phoneNum) {
        this.userName = userName;
        this.isAdmin = isAdmin;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
