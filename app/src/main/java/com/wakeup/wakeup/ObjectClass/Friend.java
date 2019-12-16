package com.wakeup.wakeup.ObjectClass;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Friend extends User implements Comparable, Parcelable {
    private String userName;
    private boolean checked;
    private boolean isAwake;
    private String phoneNumber;

    public Friend(String userName) {
        this.userName = userName;
    }

    public Friend(String userName, boolean isAwake) {
        this.userName = userName;
        this.isAwake = isAwake;
        this.phoneNumber = "012345";
    }

    // Testing Purpose
    public Friend(String userName, String phoneNumber) {
        this.userName = userName;
        this.checked = false;
        this.isAwake = false;
        // Testing purpose
        this.phoneNumber = phoneNumber;
    }

    protected Friend(Parcel in) {
        userName = in.readString();
        checked = in.readByte() != 0;
        isAwake = in.readByte() != 0;
        phoneNumber = in.readString();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    @Override
    public int compareTo(Object o) {
        return -Boolean.compare(((Friend) o).getIsAwake(), getIsAwake());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeByte((byte) (checked ? 1 : 0));
        dest.writeByte((byte) (isAwake ? 1 : 0));
        dest.writeString(phoneNumber);
    }
}
