package com.wakeup.wakeup.ObjectClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Alarm  implements Parcelable {

    private String alarmKey;
    private String time;
    private String alarmName;
    private boolean isGroup;
    private boolean isOn;
    private int gameOption;
    private String user;

    // Constructor
    public Alarm() {
    }

    public Alarm(String time, String alarmName, boolean isOn, boolean isGroup, int gameOption) {
        this.time = time;
        this.alarmName = alarmName;
        this.isOn = isOn;
        this.isGroup = isGroup;
        this.gameOption = gameOption;
    }

    // Parcel
    public Alarm(Parcel parcel) {
        time = parcel.readString();
        alarmName = parcel.readString();
        isOn = parcel.readByte() != 0;
        isGroup = parcel.readByte() != 0;
        gameOption = parcel.readInt();
        alarmKey = parcel.readString();
    }

    public static Creator<Alarm> getCREATOR() {
        return CREATOR;
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
        parcel.writeInt(gameOption);
        parcel.writeString(alarmKey);
    }
    // Get
    public String getAlarmKey() {
        return alarmKey;
    }

    public String getAlarmName() {
        return alarmName;
    }

    @PropertyName("isGroup")
    public boolean isGroup() {
        return isGroup;
    }

    @PropertyName("isOn")
    public boolean isOn() {
        return isOn;
    }

    public int getGameOption() {
        return gameOption;
    }

    public String getUser() {
        return user;
    }

    public String getTime() { //whole date in String format
        return time;
    }

    @Exclude
    public Date getTimeDate() throws ParseException { //whole date in date format
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormatter.parse(time);
        return date;
    }

    @Exclude
    public Calendar getTimeInCalender(){
        String[] details = time.split(":");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,Integer.parseInt(details[0]));
        c.set(Calendar.MINUTE,Integer.parseInt(details[1]));
        return  c;
    }

    @Exclude
    public String getTimeDisplay() throws ParseException { // short date for display
        SimpleDateFormat dateFormatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat stringFormatter =new SimpleDateFormat("hh:mm");

        Date date = dateFormatter.parse(time);
        String strTime = stringFormatter.format(date);
        return strTime;
    }

    // Set
    public void setAlarmKey(String alarmKey) {
        this.alarmKey = alarmKey;
    }

    // Firebase
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("alarmKey", alarmKey);
        result.put("time", time);
        result.put("alarmName", alarmName);
        result.put("isGroup", isGroup);
        result.put("isOn", isOn);
        result.put("gameOption", gameOption);

        return result;
    }
}
