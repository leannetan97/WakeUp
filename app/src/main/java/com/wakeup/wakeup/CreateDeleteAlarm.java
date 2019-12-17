package com.wakeup.wakeup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.backup.FileBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wakeup.wakeup.GroupTab.GroupAlarmDetailsFragment;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.ObjectClass.Group;
import com.wakeup.wakeup.PersonalAlarmTab.PersonalAlarmDetailsFragment;
import com.wakeup.wakeup.ObjectClass.Alarm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;

public class CreateDeleteAlarm extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener { //AdapterView.OnItemSelectedListener

    private String viewTitle, buttonName;

    // data
//    private DatabaseReference dbAlarms;
    private Alarm prevAlarm;
    private Alarm newAlarm;
    private String alarmKey;
    private FirebaseHelper firebaseHelper;

    private String groupKey;

    private DecimalFormat digitFormatter = new DecimalFormat("00");
//    private TextView tvAlarmName;
    private TextView tvTimeDisplay;
    private Spinner spinnerGameOption;
    private int gameOption = 0;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_delete_alarm);
        setViewToInstanceVar();
        firebaseHelper = new FirebaseHelper();
        groupKey = getIntent().getExtras().getString("GroupKey");
        group = getIntent().getExtras().getParcelable("Group");
        viewTitle = getIntent().getExtras().getString("ViewTitle");
        buttonName = getIntent().getExtras().getString("ButtonName");
        updateViewDetails();

        updateActionBarColor();

        Fragment fragment = null;
        if (viewTitle.contains("Edit")) {
            prevAlarm = getIntent().getExtras().getParcelable("AlarmData");
            alarmKey = prevAlarm.getAlarmKey();

            //set up view with previous data
            if (prevAlarm.isGroup()) { //isGroup
                System.out.println("[DEBUG] Group Details Fragment");
                fragment = new GroupAlarmDetailsFragment(prevAlarm.getAlarmName());
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

    @Override
    protected void onStart() {
        super.onStart();
        setViewToInstanceVar();
        // Update value of Spinner not work at onCreate
        if (viewTitle.contains("Edit")) {
            //TODO: Update Spinner value
            System.out.println("[DEBUG] prevAlarm.getGameOption() :"+ prevAlarm.getGameOption());
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
            if(prevAlarm.isOn()){
                System.out.println("Cancel Alarm");
                cancelAlarm();
            }
            System.out.println("Delete Alarm");
            deleteAlarm();
            finish();
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void updateActionBarColor(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_bg_purple));
    }

    private void setDefaultTimeDisplay(){
        Calendar c = Calendar.getInstance();
//        alarmCalendar = c;
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        String time = digitFormatter.format(hourOfDay) + ":" + digitFormatter.format(minutes);
        ((TextView)findViewById(R.id.tv_time_display)).setText(time);
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

    public void deleteAlarm() {
        //TODO: perform delete alarm
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.deleteAlarm(alarmKey);
    }

    public void updateAlarm() {
        //update alarm with existing key
        firebaseHelper.updateAlarm(newAlarm, alarmKey);
    }

    public void addAlarm(boolean isGroup) {
        if(isGroup){
            firebaseHelper.addAlarmToGroup(newAlarm, groupKey);
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
            newAlarm = new Alarm(time, alarmName, prevAlarm.isOn(), prevAlarm.isGroup(), gameOption);
//            if(prevAlarm.isOn()){
//                cancelAlarm();
//            }
//            startAlarm(alarmCalendar);
            updateAlarm();
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

//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//        //  gameOption = [NONE, TICTACTOE, MATH, SHAKER]
//        gameOption = position;
//        //TODO: For testing purpose only
//        Toast.makeText(adapterView.getContext(), Integer.toString(gameOption) + adapterView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//        //do nothing
//    }

    private void startAlarm(Calendar c) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        //Change the alarm object to byte so that pass
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(newAlarm);
            out.flush();
            byte[] data = bos.toByteArray();
            intent.putExtra("alarm", data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmKey.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmKey.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this,"Alarm is Cancel.", Toast.LENGTH_SHORT).show();
    }
}