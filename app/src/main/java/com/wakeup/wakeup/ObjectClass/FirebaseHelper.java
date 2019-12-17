package com.wakeup.wakeup.ObjectClass;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    // link to firebase
    private DatabaseReference dbFirebase;
    private DatabaseReference dbGroups;
    private DatabaseReference dbScores;

    private DatabaseReference dbUsers;
    private DatabaseReference dbUserAlarms;
    private DatabaseReference dbUserHistory;
    private DatabaseReference dbUserGroups;
    // private DatabaseReference dbCurrentUser;


    // get current user and email
    String username;
    String phoneNum;


    public FirebaseHelper() {
        dbFirebase = FirebaseDatabase.getInstance().getReference();
        dbUsers = FirebaseDatabase.getInstance().getReference("users");
        dbGroups = FirebaseDatabase.getInstance().getReference("groups");
        dbScores = FirebaseDatabase.getInstance().getReference("scores");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            phoneNum = user.getPhoneNumber();
            username = user.getDisplayName();
            Log.e("addCur", "current user: " + user);

            // all nodes under current user
            dbUserAlarms = dbUsers.child(phoneNum).child("alarms");
            dbUserHistory = dbUsers.child(phoneNum).child("history");
            dbUserGroups = dbUsers.child(phoneNum).child("groups");
        }
    }


    // Alarm
    public void addAlarm(Alarm alarm) {
        String id = dbUserAlarms.push().getKey();
        updateAlarm(alarm, id);
    }

    public void updateAlarm(Alarm alarm, String alarmKey) {
        dbUserAlarms.child(alarmKey).setValue(alarm);
    }

    public void deleteAlarm(String alarmKey) {
        dbUserAlarms.child(alarmKey).removeValue();
    }


    // Group
    public String addGroup(Group group) {
        String id = dbGroups.push().getKey();
        updateGroup(group, id);

        addAdminToGroup(phoneNum, id);
        return id;
    }

    public void updateGroup(Group group, String groupKey) {
        modifyGroup(group, groupKey, true);
    }

    public void deleteGroup(Group group, String groupKey) {
        modifyGroup(group, groupKey, false);
    }

    public void modifyGroup(Group group, String groupKey, boolean value) {
        String groupName = null;
        if (value) {
            groupName = group.getGroupName();
        }

        // add to current user
        Log.d("add", "here " + this.phoneNum);
        addUserToGroup(phoneNum, groupKey, groupName);

        // add other users
        for (User user : group.getUsersInGroup()) {
            addUserToGroup(user.getPhoneNum(), groupKey, groupName);
        }
    }


    // users and groups
    public void addUserToGroup(String phoneNum, String groupKey, Object value) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + phoneNum + "/groups/" + groupKey, value);
        childUpdates.put("/groups/" + groupKey + "/users/" + phoneNum, value);
        Log.d("add", "/users/" + phoneNum + "/groups/" + groupKey);

        dbFirebase.updateChildren(childUpdates);
    }

    public void removeUserFromGroup(String phoneNum, String groupKey) {
        addUserToGroup(phoneNum, groupKey, null);
    }

    public void addAdminToGroup(String phone, String groupKey) {
        dbGroups.child(groupKey).child("admins").child(phone).setValue(true);
    }

    // alarms and groups
    public void addAlarmToGroup(Alarm alarm, String groupKey) {
        dbGroups.child(groupKey).child("alarms").push().setValue(alarm);
    }

    public void deleteAlarmFromGroup(String alarmKey, String groupKey) {
        dbGroups.child(groupKey).child("alarms").child(alarmKey).setValue(null);
    }

    public void updateAlarmOfGroup(Alarm alarm, String alarmKey, String groupKey) {
        dbGroups.child(groupKey).child("alarms").child(alarmKey).setValue(alarm);
    }



    // Games
    public void addScore(Game game) {
        String id = dbScores.push().getKey();
        dbUserAlarms.child(id).setValue(game);
    }


    // History
    public void addHistory(int delay) {
        History history = new History(delay, ServerValue.TIMESTAMP);

        String id = dbScores.push().getKey();
        dbUserHistory.child(id).setValue(history);
    }







    // Get
    public DatabaseReference getDbUsers() {
        return dbUsers;
    }

    public DatabaseReference getDbUserAlarms() {
        return dbUserAlarms;
    }

    public DatabaseReference getDbGroups() {
        return dbGroups;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public DatabaseReference getDbFirebase() {
        return dbFirebase;
    }

    public DatabaseReference getDbScores() {
        return dbScores;
    }

    public DatabaseReference getDbUserHistory() {
        return dbUserHistory;
    }

    public DatabaseReference getDbUserGroups() {
        return dbUserGroups;
    }
}
