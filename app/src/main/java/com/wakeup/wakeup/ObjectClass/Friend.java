package com.wakeup.wakeup.ObjectClass;

import androidx.annotation.NonNull;

public class Friend extends User{
    private String userName;
    private boolean checked;
    private boolean isAwake;
    private String email;

    public Friend() {

    }

    // Testing Purpose
    public Friend(String userName) {
        this.userName = userName;
        this.checked = false;
        this.isAwake = false;
        // Testing purpose
        this.email = this.userName + "@abc.com";
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public void setIsAwake(boolean isAwake) {
        this.isAwake = isAwake;
    }

    public boolean getIsAwake() {
        return this.isAwake;
    }

    @NonNull
    @Override
    public String toString() {
        return this.userName;
    }
}
