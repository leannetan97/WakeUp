package com.wakeup.wakeup.ObjectClass;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    // link to firebase
    private DatabaseReference dbFirebase;
    private DatabaseReference dbUsers;
    private DatabaseReference dbCurrentUser;
    private DatabaseReference dbUserAlarms;
    private DatabaseReference dbGroups;

    // get current user and email
    String username;
    String email;
    String emailHashed;


    public FirebaseHelper() {
        dbFirebase = FirebaseDatabase.getInstance().getReference();
        dbUsers = FirebaseDatabase.getInstance().getReference("users");
        dbGroups = FirebaseDatabase.getInstance().getReference("groups");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            username = user.getDisplayName();
            email = user.getEmail();
            emailHashed = String.valueOf(email.hashCode());
            dbUserAlarms = dbUsers.child(emailHashed).child("alarms");
            dbCurrentUser = dbUsers.child(emailHashed);
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

    public void addGroup(Group group) {
        String id = dbGroups.push().getKey();
        updateGroup(group, id);
    }

    public void updateGroup(Group group, String groupKey) {
        handleGroup(group, groupKey, true);
    }

    public void deleteGroup(Group group, String groupKey) {
        handleGroup(group, groupKey, null);
    }


    public void handleGroup(Group group, String groupKey, Object value) {
        // add to current user
        addUserGroup(emailHashed, groupKey, value);

        // add other users
        for (User user : group.getUsersInGroup()) {
            addUserGroup(user.getEmailHashed(), groupKey, value);
        }
    }

    // relationship between users and groups
    public void addUserGroup(String emailKeyHashed, String groupKey, Object value) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + emailKeyHashed + "/groups", value);
        childUpdates.put("/groups/" + groupKey + "/users", value);

        dbFirebase.updateChildren(childUpdates);
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

    public String getEmail() {
        return email;
    }
}
