package com.wakeup.wakeup.ObjectClass;


import java.util.Map;

public class History {
    public int delay;
    public Map<String, String> timestamp;

    public History() {
    }

    public History(int delay, long timestamp) {
        this.delay = delay;
//        this.timestamp = timestamp;
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

    //    private SimpleDateFormat dateFormatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



}
