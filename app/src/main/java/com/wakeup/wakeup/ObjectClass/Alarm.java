package com.wakeup.wakeup.ObjectClass;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alarm  implements Parcelable {

    private String time;
    private String alarmName;
    private boolean isGroup;
    private boolean isOn;
    private int game;
    private String user;

    public Alarm() {
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    public Alarm(String time, String alarmName, boolean isOn, boolean isGroup, int game) {
        this.time = time;
        this.alarmName = alarmName;
        this.isOn = isOn;
        this.isGroup = isGroup;
        this.game = game;
    }

    public Alarm(Parcel parcel) {
        time = parcel.readString();
        alarmName = parcel.readString();
        isOn = parcel.readByte() != 0;
        isGroup = parcel.readByte() != 0;
        game = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //The sequence is really important (Need to match back Alarm(Parcel parcel) sequence
        parcel.writeString(time);
        parcel.writeString(alarmName);
        parcel.writeByte((byte) (isOn ? 1 : 0));
        parcel.writeByte((byte) (isGroup ? 1 : 0));
        parcel.writeInt(game);
    }

    public static Creator<Alarm> getCREATOR() {
        return CREATOR;
    }

    public String getTime() {
        return time;
    }

    public Date getTimeinDate() throws ParseException {
        SimpleDateFormat dateFormatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormatter.parse(time);
        return date;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public int getGame() {
        return game;
    }

    public String getUser() {
        return user;
    }

    public boolean isOn() {
        return isOn;
    }
}
