package com.wakeup.wakeup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.backup.FileBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wakeup.wakeup.GroupTab.GroupAlarmDetailsFragment;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.ObjectClass.Friend;
import com.wakeup.wakeup.ObjectClass.Group;
import com.wakeup.wakeup.ObjectClass.GroupMember;
import com.wakeup.wakeup.PersonalAlarmTab.PersonalAlarmDetailsFragment;
import com.wakeup.wakeup.ObjectClass.Alarm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.stream.Collectors;

public class CreateDeleteAlarm extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener { //AdapterView.OnItemSelectedListener

    private String viewTitle, buttonName;

    // data
//    private DatabaseReference dbAlarms;
    private Alarm prevAlarm;
    private Alarm newAlarm;
    private String alarmKey;
    private FirebaseHelper firebaseHelper;


    private DecimalFormat digitFormatter = new DecimalFormat("00");
    //    private TextView tvAlarmName;
    private TextView tvTimeDisplay;
    private Spinner spinnerGameOption;

    // Group Edit variables
    private Group group;
    private ArrayList<Friend> friendsContact;
    private ArrayList<GroupMember> allContacts;
    private String groupKey;
    private DatabaseReference dbGroups;
    String currentUserPhoneNum;
    ActionBar tb;
    private ListView lvAwake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_delete_alarm);
        setViewToInstanceVar();

//        groupKey = getIntent().getExtras().getString("GroupKey");
//        group = getIntent().getExtras().getParcelable("Group");

        viewTitle = getIntent().getExtras().getString("ViewTitle");
        buttonName = getIntent().getExtras().getString("ButtonName");

        firebaseHelper = new FirebaseHelper();
        updateViewDetails();

        updateActionBarColor();

        Fragment fragment = null;
        if (viewTitle.contains("Edit")) {
            prevAlarm = getIntent().getExtras().getParcelable("AlarmData");
            alarmKey = prevAlarm.getAlarmKey();

            //set up view with previous data
            if (prevAlarm.isGroup()) { //isGroup
                System.out.println("[DEBUG] Group Details Fragment");
                groupKey = getIntent().getExtras().getString("GroupKey");
                allContacts = getIntent().getExtras().getParcelableArrayList("AllContacts");
                group = getIntent().getExtras().getParcelable("Group");
                updateFilteredFriendsList();
                fragment = new GroupAlarmDetailsFragment(prevAlarm.getAlarmName(), friendsContact
                        , groupKey);
//                fragment = new GroupAlarmDetailsFragment(prevAlarm.getAlarmName());
            } else { //isPersonal
                System.out.println("[DEBUG] Personal Details Fragment");
                fragment = new PersonalAlarmDetailsFragment(prevAlarm.getAlarmName());
            }
        } else {
            //A Default time
            setDefaultTimeDisplay();
            fragment = new PersonalAlarmDetailsFragment("Alarm");
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_alarm_details, fragment);
        transaction.commit();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.game_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGameOption.setAdapter(adapter);
//        spinnerGameOption.setOnItemSelectedListener(this);
    }

    protected void onStart() {
        super.onStart();
        setViewToInstanceVar();
        // Update value of Spinner not work at onCreate
        if (viewTitle.contains("Edit")) {
            //TODO: Update Spinner value
            System.out.println("[DEBUG] prevAlarm.getGameOption() :" + prevAlarm.getGameOption());
            spinnerGameOption.setSelection(prevAlarm.getGameOption());
            tvTimeDisplay.setText(prevAlarm.getTime());



    }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!viewTitle.contains("New")) {
            getMenuInflater().inflate(R.menu.alarm_edit_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btn_delete_alarm) {
            if (prevAlarm.isOn()) {
                System.out.println("Cancel Alarm");
                cancelAlarm();
            }
            System.out.println("Delete Alarm");
            deleteAlarm(prevAlarm.isGroup());
            finish();
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
    // Start Filter
    private void updateFilteredFriendsList(){
        if (prevAlarm.isGroup()) {
            dbGroups.child(groupKey).child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    tb = getSupportActionBar();
                    tb.setDisplayHomeAsUpEnabled(true);
                    tb.setTitle("Awake Status List");
                    int n;
                    friendsContact.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String phoneNum = postSnapshot.getKey();
                        boolean isAwake = (boolean) postSnapshot.getValue();

                        n = allContacts.stream().filter(o -> phoneNum.equals(o.getPhoneNum())).collect(Collectors.toList()).size();

                        String name;
                        if (currentUserPhoneNum.equals(phoneNum)) {
                            continue;
                        } else if (n > 0) {
                            name = allContacts.stream().filter(o -> phoneNum.equals(o.getPhoneNum())).collect(Collectors.toList()).get(0).getUserName();
                        } else {
                            name = phoneNum;
                        }
                        friendsContact.add(new Friend(name, isAwake, phoneNum));
                    }
                    Collections.sort(friendsContact);

                    int nFriendsNotAwake;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        nFriendsNotAwake =
                                friendsContact.stream().filter(o -> !o.getIsAwake()).collect(Collectors.toList()).size();
                    } else {
                        nFriendsNotAwake = 0;
                        for (Friend f : friendsContact) {
                            if (!f.getIsAwake()) {
                                nFriendsNotAwake += 1;
                            }
                        }
                    }

                    if (nFriendsNotAwake > 0) {
                        tb.setTitle("(" + nFriendsNotAwake + ") is/are still sleeping");
                    } else {
                        tb.setTitle("All members are awake!");
                    }
                    lvAwake = (ListView) findViewById(R.id.lv_awakeStatusList_awake);
                    CustomAdapter customAdapterAwake =
                            new CustomAdapter(getApplicationContext(), friendsContact);
                    lvAwake.setAdapter(customAdapterAwake);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void updateActionBarColor() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_bg_purple));
    }

    private void setDefaultTimeDisplay() {
        Calendar c = Calendar.getInstance();
//        alarmCalendar = c;
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        String time = digitFormatter.format(hourOfDay) + ":" + digitFormatter.format(minutes);
        ((TextView) findViewById(R.id.tv_time_display)).setText(time);
    }

    private void updateViewDetails() {
        getSupportActionBar().setTitle(viewTitle);
        ((Button) findViewById(R.id.btn_create_save_alarm)).setText(buttonName);
    }

    private void setViewToInstanceVar() {
        tvTimeDisplay = findViewById(R.id.tv_time_display);
//        tvAlarmName = findViewById(R.id.tv_alarm_name);
        spinnerGameOption = findViewById(R.id.input_spinner);
    }

    public void deleteAlarm(boolean isGroup) {
        //TODO: perform delete alarm
        if (isGroup) {
            System.out.println(groupKey);
            firebaseHelper.deleteAlarmFromGroup(alarmKey, group);
        } else {
//            FirebaseHelper firebaseHelper = new FirebaseHelper();
            firebaseHelper.deleteAlarm(alarmKey);
        }
    }

    public void updateAlarm(boolean isGroup) {
        //update alarm with existing key
        if (isGroup) {
            firebaseHelper.updateAlarmOfGroup(newAlarm, group, alarmKey);
        } else {
            firebaseHelper.updateAlarm(newAlarm, alarmKey);
        }
    }

    public void addAlarm(boolean isGroup) {
        if (isGroup) {
//            Log.d("group", group.getUsersInGroup().get(0).getPhoneNum());
//            System.out.println(group.getUsersInGroup().get(0).getPhoneNum());
            System.out.println("group");
            firebaseHelper.addAlarmToGroup(newAlarm, group);
        } else {
            firebaseHelper.addAlarm(newAlarm);
        }
    }

    public void submitButtonOnClick(View view) {
        //TODO: Save details in local database
        setViewToInstanceVar();
        int gameOption = spinnerGameOption.getSelectedItemPosition();
        String time = (String) tvTimeDisplay.getText();
        String alarmName = (String) ((TextView)findViewById(R.id.tv_alarm_name)).getText();
        if (viewTitle.contains("Edit")) {
            newAlarm = new Alarm(time, alarmName, true, prevAlarm.isGroup(), gameOption);

            updateAlarm(prevAlarm.isGroup());
        } else if (viewTitle.contains("Personal")) {
            newAlarm = new Alarm(time, alarmName, true, false, gameOption);
//            startAlarm(alarmCalendar);
            addAlarm(false);
        } else {
            newAlarm = new Alarm(time, alarmName, true, true, gameOption);
//            startAlarm(alarmCalendar);
            addAlarm(true);
        }
        finish();
    }

    public void showTimePickerDialog(View v) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, hour, minute,
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
        String time = digitFormatter.format(hourOfDay) + ":" + digitFormatter.format(minutes);
        tvTimeDisplay.setText(time);
        //TODO: need to store the calender?
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minutes);
//        alarmCalendar = c;
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmKey.hashCode(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm is Cancel.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // For Group Awake Status Custom Adapter
    class CustomAdapter extends ArrayAdapter<Friend> {
        Context context;

        ArrayList<Friend> friends;

        CustomAdapter(Context c, ArrayList<Friend> friends) {
            super(c, R.layout.res_layout_row_awake_status_list, R.id.tv_friendName, friends);
            this.context = c;
            this.friends = friends;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View row =
                    layoutInflater.inflate(R.layout.res_layout_row_awake_status_list, parent,
                    false);

            TextView tvFriendName = row.findViewById(R.id.tv_friendName);
            tvFriendName.setText(friends.get(position).getUserName());

            TextView tvFriendPhone = row.findViewById(R.id.tv_friendPhoneNumber);
            System.out.println(friends.get(position).getPhoneNum());
            tvFriendPhone.setText(friends.get(position).getPhoneNum());

            ImageView ivAwakeStatus = row.findViewById(R.id.iv_awakeStatus);
            ImageView btnCall = row.findViewById(R.id.btn_call);
            if (friends.get(position).getIsAwake()) {
                ivAwakeStatus.setImageResource(R.drawable.ic_awake_green);
            } else {
                ivAwakeStatus.setImageResource(R.drawable.ic_sleep_red);
            }
//            btnCall.setBackgroundTintList(context.getResources().getColorStateList(R.color
//            .colorAccent));
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "call " + friends.get(position).getUserName(),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + friends.get(position).getPhoneNum()));
                    startActivity(intent);


                }
            });
            return row;
        }
    }
}