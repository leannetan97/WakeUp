package com.wakeup.wakeup.ObjectClass;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    String phoneNum;

    // Constructor
    public User() {
    }

    public User(String phone) {
        this.phoneNum = phone;
    }

    protected User(Parcel in) {
        phoneNum = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phoneNum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static Parcelable.Creator<User> getCREATOR() {
        return CREATOR;
    }





    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


// check email exists -->signup, checkpw
}
