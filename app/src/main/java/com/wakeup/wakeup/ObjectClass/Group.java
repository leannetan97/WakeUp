package com.wakeup.wakeup.ObjectClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable, Parcelable {
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

    // parcel
    protected Group(Parcel in) {
        groupKey = in.readString();
        groupName = in.readString();
        alarmsInGroup = in.createTypedArrayList(Alarm.CREATOR);
        usersInGroup = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupKey);
        dest.writeString(groupName);
        dest.writeTypedList(alarmsInGroup);
        dest.writeTypedList(usersInGroup);
    }
}
