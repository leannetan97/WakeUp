package com.wakeup.wakeup.ObjectClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Group {
    private String groupName;
    private List<Alarm> alarmsInGroup;

    public Group(String groupName){
        this.groupName = groupName;
        alarmsInGroup = new ArrayList<>();
    }

    public void addAlarm(Alarm alarmItem){
        alarmsInGroup.add(alarmItem);
    }

    public List<Alarm> getAlarmsInGroup() {
        return alarmsInGroup;
    }

    public Iterator<Alarm> getAlarmsInGroupIterator(){
        return alarmsInGroup.iterator();
    }

    public String getGroupName() {
        return groupName;
    }
}
