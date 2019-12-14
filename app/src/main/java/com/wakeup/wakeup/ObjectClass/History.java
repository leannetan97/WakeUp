package com.wakeup.wakeup.ObjectClass;


import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class History {
    private int delay;
    private Map<String, String> timestamp;
    private Long date;

    public History() {
    }

    public History(int delay, Map timestamp) {
        this.delay = delay;
        this.timestamp = timestamp;
    }

    public History(int delay, Long date) {
        this.delay = delay;
        this.date = date;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public Map<String, String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Map<String, String> timestamp) {
        this.timestamp = timestamp;
    }

    @Exclude
    public String getDate() {
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM   HH:mm");
            return dateFormat.format(date);
        } catch(Exception e) {
            return "date";
        }
    }

    public void setDate(Long date) {
        this.date = date;
    }

    //    private SimpleDateFormat dateFormatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



}
