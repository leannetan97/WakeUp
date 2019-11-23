package com.wakeup.wakeup.ObjectClass;

import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wakeup.wakeup.R;

public class FirebaseHelper {
    // link to firebase
    private DatabaseReference dbUsers;
    private DatabaseReference dbAlarms;
    private DatabaseReference dbGroups;

    // get current user and email
    String username;
    String email;

    public FirebaseHelper() {
        dbUsers = FirebaseDatabase.getInstance().getReference("users");
        dbAlarms = FirebaseDatabase.getInstance().getReference("alarms");
        dbGroups = FirebaseDatabase.getInstance().getReference("groups");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            username = user.getDisplayName();
            email = user.getEmail();
        }
    }



    // add node
    public void addUser() {
        String hashed = String.valueOf(email.hashCode());

        User user = new User(email);
        dbUsers.child(hashed).setValue(user);
    }

    public void addAlarm() {
        String id = dbAlarms.push().getKey();

        Alarm alarm = new Alarm("2019-12-30 23:37:51","AlarmSatu",true, true,  1);
        dbAlarms.child(id).setValue(alarm);
    }



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
