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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private DatabaseReference dbUserGroupAlarms;
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
            if (username.length() == 0) {
                username = phoneNum.substring(phoneNum.length() - 4);
            }

            Log.e("addCur", "current user: " + user);

            // all nodes under current user
            dbUserAlarms = dbUsers.child(phoneNum).child("alarms");
            dbUserHistory = dbUsers.child(phoneNum).child("history");
            dbUserGroups = dbUsers.child(phoneNum).child("groups");
            dbUserGroupAlarms = dbUsers.child(phoneNum).child("groupalarms");
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
        modifyGroup(group, groupKey);
    }

    public void deleteGroup(Group group) {
        String groupKey = group.getGroupKey();
//        dbGroups.child(groupKey).setValue(null);
//        addUserToGroup(phoneNum, groupKey, null);
        dbGroups.child(groupKey).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    addUserToGroup(postSnapshot.getKey(), groupKey, null);
                }
                dbUsers.child(phoneNum).child("groups").child(groupKey).removeValue();
                // add other users
                dbGroups.child(groupKey).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        for (User user : newGroup.getUsersInGroup()) {
//            System.out.println(user.getPhoneNum());
//            System.out.println("lkdsfa");
////            dbUsers.child(user.getPhoneNum()).child("groups").child(groupKey).removeValue();
//            addUserToGroup(user.getPhoneNum(), groupKey, null);
//        }
        dbUsers.child(phoneNum).child("groups").child(groupKey).removeValue();


        // add other users

        dbGroups.child(groupKey).removeValue();

    }

    public void modifyGroup(Group group, String groupKey) {
        String groupName = group.getGroupName();
//        boolean groupName = false;

        // add to current user
        Log.d("add", "here " + this.phoneNum);
        addUserToGroup(phoneNum, groupKey, groupName);

        // add other users
        if (group != null) {
            for (User user : group.getUsersInGroup()) {
                addUserToGroup(user.getPhoneNum(), groupKey, groupName);
            }
        }

    }

    public void setUserAwake(String groupKey) {
        dbGroups.child(groupKey).child("users").child(phoneNum).setValue(true);
    }


    // users and groups
    public void addUserToGroup(String phoneNum, String groupKey, Object value) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + phoneNum + "/groups/" + groupKey, value);
        childUpdates.put("/groups/" + groupKey + "/users/" + phoneNum, false);
        Log.d("add", "/users/" + phoneNum + "/groups/" + groupKey);

        dbFirebase.updateChildren(childUpdates);
    }

    public void removeUserFromGroup(String phoneNum, String groupKey) {
        addUserToGroup(phoneNum, groupKey, null);

        dbGroups.child(groupKey).child("users").child(phoneNum).setValue(null);
    }

    public void addAdminToGroup(String phone, String groupKey) {
        dbGroups.child(groupKey).child("admins").child(phone).setValue(true);
    }

    // alarms and groups
    public void addAlarmToGroup(Alarm alarm, Group group) {
        String groupKey = group.getGroupKey();
        String groupAlarmKey = dbGroups.child(groupKey).child("alarms").push().getKey();

        alarm.setGroupKey(groupKey);
        updateAlarmOfGroup(alarm, group, groupAlarmKey);
    }

    public void deleteAlarmFromGroup(String groupAlarmKey, Group group) {
        String groupKey = group.getGroupKey();

        dbGroups.child(groupKey).child("alarms").child(groupAlarmKey).setValue(null);
        dbUserGroupAlarms.child(groupAlarmKey).setValue(null);

        Map<String, Object> childUpdates = new HashMap<>();
        for (User user : group.getUsersInGroup()) {
            childUpdates.put("/users/" + user.getPhoneNum() + "/groupalarms/" + groupAlarmKey, null);
        }

        dbFirebase.updateChildren(childUpdates);
    }

    public void updateAlarmOfGroup(Alarm alarm, Group group, String groupAlarmKey) {
        String groupKey = group.getGroupKey();

        dbUserGroupAlarms.child(groupAlarmKey).setValue(alarm);

        //add to every user
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/groups/" + groupKey + "/alarms/" + groupAlarmKey, alarm);

        for (User user : group.getUsersInGroup()) {
            childUpdates.put("/users/" + user.getPhoneNum() + "/groupalarms/" + groupAlarmKey, alarm);
        }

        dbFirebase.updateChildren(childUpdates);
    }
    //alarms and groups using groupKey
    public Group getGroup(String groupKey) {
        Group newGroup = new Group();
        newGroup.setGroupKey(groupKey);

        List<Alarm> alarmsInGroup = new ArrayList<>();
        List<User> usersInGroup = new ArrayList<>();

        //get group name
        dbUsers.child("groups").child(groupKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                newGroup.setGroupName(dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //get users
        dbGroups.child(groupKey).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    usersInGroup.add(new User(postSnapshot.getKey()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //get alarms
        dbGroups.child(groupKey).child("alarms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    alarmsInGroup.add(postSnapshot.getValue(Alarm.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        newGroup.setAlarmsInGroup(alarmsInGroup);
        newGroup.setUsersInGroup(usersInGroup);
        return newGroup;
    }

    public void deleteAlarmFromGroup(String groupAlarmKey, String groupKey) {
        Group group = getGroup(groupKey);
        deleteAlarmFromGroup(groupAlarmKey, group);
    }

    public void updateAlarmOfGroup(Alarm alarm, String groupKey, String groupAlarmKey) {
        Group group = getGroup(groupKey);
        updateAlarmOfGroup(alarm, group, groupAlarmKey);
    }

    // Games
    public void addScore(Game game) {
        String id = dbScores.push().getKey();
        game.setUser(username);

        dbScores.child(id).setValue(game);

//        String id = dbScores.child(game.getPhoneNum()).push().getKey();
//        game.setUser(username);
//
//        dbScores.child(game.getPhoneNum()).child(id).setValue(game);
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

    public DatabaseReference getDbUserGroupAlarms() {
        return dbUserGroupAlarms;
    }
}
