package com.wakeup.wakeup.ObjectClass;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Friend extends User implements Comparable, Parcelable {
    private String userName;
    private boolean checked;
    private boolean isAwake;
    private String phoneNum;

    public Friend(String userName) {
        this.userName = userName;
    }

    // Testing Purpose
    public Friend(String userName, boolean isAwake) {
        this.userName = userName;
        this.isAwake = isAwake;
//        this.phoneNum = "012345";
    }


    public Friend(String userName, String phoneNum) {
        this.userName = userName;
        this.phoneNum = phoneNum;
//        this.checked = false;
//        this.isAwake = false;
    }

    protected Friend(Parcel in) {
        userName = in.readString();
        checked = in.readByte() != 0;
        isAwake = in.readByte() != 0;
        phoneNum = in.readString();
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

//    public void setPhoneNum(String phoneNumber) {
//        this.phoneNum = phoneNumber;
//    }

//    public String getPhoneNum() {
//        return phoneNum;
//    }

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
        dest.writeString(phoneNum);
    }
}
