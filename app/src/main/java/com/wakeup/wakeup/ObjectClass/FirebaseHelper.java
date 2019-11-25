package com.wakeup.wakeup.ObjectClass;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseHelper {
    // link to firebase
    private DatabaseReference dbFirebase;
    private DatabaseReference dbUsers;
    private DatabaseReference currentUser;
    private DatabaseReference dbAlarms;
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
            dbAlarms = dbUsers.child(emailHashed).child("alarms");
            currentUser = dbUsers.child(emailHashed);
        }
    }



    // add node
//    public void addUser() {
//        User user = new User(email);
//        currentUser.setValue(user);
//    }

    public void addAlarm(Alarm newAlarm) {
        String id = dbAlarms.push().getKey();
        dbAlarms.child(id).setValue(newAlarm);
    }

    public void addGroup(Group group) {
        // add to group
        String id = dbGroups.push().getKey();

        //dummy data, to be replaced
//        Group group = new Group("Newly grouped");
//        User testUser = new User("yee@gmail.com");
//        group.addUser(testUser);


        for (User user : group.getUsersInGroup()) {
            addUserGroup(user.getEmailHashed(), id);
        }

//        // add to current user
        addUserGroup(emailHashed, id);
    }

    public void addUserGroup(String emailKeyHashed, String groupKey) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + emailKeyHashed + "/groups", true);
        childUpdates.put("/groups/" + groupKey + "/users", true);

        dbFirebase.updateChildren(childUpdates);
    }

    

    // toMap
    public Map<String, Boolean> UsersToMap(List<User> usersList) {
        HashMap<String, Boolean> result = new HashMap<>();

        for (User user : usersList) {
            result.put(String.valueOf(user.getEmail().hashCode()), true);
        }
        return result;
    }




    // update
    public void updateAlarm(Alarm alarm, String alarmKey) {
        dbAlarms.child(alarmKey).setValue(alarm);
    }

    public void updateGroup() {
//    public void updateGroup(Group group, String groupKey) {
        // add to group
        String id = dbGroups.push().getKey();

        //dummy data, to be replaced
        Group group = new Group("Newly grouped");
        User testUser = new User("yee@gmail.com");
        group.addUser(testUser);

        for (User user : group.getUsersInGroup()) {
            dbGroups.child(id).child("users").child(user.getEmailHashed()).setValue(true);
        }

        // add to user
        currentUser.child("groups").child(id).setValue(true);

//        //update children
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("users");
    }



    // delete















    // getter
    public DatabaseReference getDbUsers() {
        return dbUsers;
    }

    public DatabaseReference getDbAlarms() {
        return dbAlarms;
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
    ////
}
