package com.wakeup.wakeup.ObjectClass;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
    private String groupKey;
    private String groupName;
    private List<Alarm> alarmsInGroup;
    private List<User> usersInGroup;

    public Group() {
    }

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
    @Exclude
    public String getGroupKey() {
        return groupKey;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<User> getUsersInGroup() {
        return usersInGroup;
    }

    public List<Alarm> getAlarmsInGroup() {
        return alarmsInGroup;
    }

//    public HashMap<String, Boolean> getAlarmsInGroup() {
//        HashMap<String, Boolean> map = new HashMap<>();
//        for (User user : usersInGroup) {
//            map.put(String.valueOf(user.getEmail().hashCode()), true);
//        }
//        return map;
//    }
//
//    public HashMap<String, Boolean> getUsersInGroup() { //for firebase
//        HashMap<String, Boolean> map = new HashMap<>();
//        for (User user : usersInGroup) {
//            map.put(String.valueOf(user.getEmail().hashCode()), true);
//        }
//        return map;
//    }


//    public Iterator<Alarm> getAlarmsInGroupIterator(){
//        return alarmsInGroup.iterator();
//    }

    // Set
    @Exclude
    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
}
