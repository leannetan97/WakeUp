package com.wakeup.wakeup.ObjectClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Group {
    private String groupKey;
    private String groupName;
    private List<Alarm> alarmsInGroup;
    private List<User> usersInGroup;

    public Group(String groupName){
        this.groupName = groupName;
        this.alarmsInGroup = new ArrayList<>();
        this.usersInGroup = new ArrayList<>();
    }

    public void addAlarm(Alarm alarmItem) {
        alarmsInGroup.add(alarmItem);
    }

    public void addUser(User userItem) {
        usersInGroup.add(userItem);
    }

    // Get
    public String getGroupKey() {
        return groupKey;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<Alarm> getAlarmsInGroup() {
        return alarmsInGroup;
    }

    public List<User> getUsersInGroup() {
        return usersInGroup;
    }

    public Iterator<Alarm> getAlarmsInGroupIterator(){
        return alarmsInGroup.iterator();
    }

    // Set
    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
}
