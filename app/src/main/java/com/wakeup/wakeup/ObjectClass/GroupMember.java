package com.wakeup.wakeup.ObjectClass;

import android.os.Parcel;
import android.os.Parcelable;

public class GroupMember extends User implements Parcelable {
    private String userName;
    private boolean isAdmin;
    private String phoneNum;

    public GroupMember(String userName, boolean isAdmin, String phoneNum) {
        this.userName = userName;
        this.isAdmin = isAdmin;
        this.phoneNum = phoneNum;
    }

    protected GroupMember(Parcel in) {
        userName = in.readString();
        isAdmin = in.readByte() != 0;
        phoneNum = in.readString();
    }

    public static final Creator<GroupMember> CREATOR = new Creator<GroupMember>() {
        @Override
        public GroupMember createFromParcel(Parcel in) {
            return new GroupMember(in);
        }

        @Override
        public GroupMember[] newArray(int size) {
            return new GroupMember[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeByte((byte) (isAdmin ? 1 : 0));
        dest.writeString(phoneNum);
    }
}
