package com.wakeup.wakeup;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryModel {

    private Date date;
    private int delay;
    private SimpleDateFormat dateFormatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public HistoryModel(Date date, int delay) {
        this.date = date;
        this.delay = delay;
    }

    public SimpleDateFormat getDateFormatter() {
        return dateFormatter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
