package com.wakeup.wakeup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alarm {

    private String time;
    private String alarmName;
    private boolean isGroup;
    private int game;
    private String user;


    public Alarm() {
    }

    public Alarm(String time, String alarmName, boolean isGroup, int game) {
        this.time = time;
        this.alarmName = alarmName;
        this.isGroup = isGroup;
        this.game = game;
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

}
