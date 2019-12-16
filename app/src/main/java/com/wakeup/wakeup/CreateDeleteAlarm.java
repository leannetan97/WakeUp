package com.wakeup.wakeup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wakeup.wakeup.GroupTab.GroupAlarmDetailsFragment;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.PersonalAlarmTab.PersonalAlarmDetailsFragment;
import com.wakeup.wakeup.ObjectClass.Alarm;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;

public class CreateDeleteAlarm extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {

    private String viewTitle, buttonName;

    // data
//    private DatabaseReference dbAlarms;
    private Alarm prevAlarm;
    private Alarm newAlarm;
    private String alarmKey;
    private FirebaseHelper firebaseHelper;

    private DecimalFormat digitFormatter = new DecimalFormat("00");
    private TextView tvAlarmName;
    private TextView tvTimeDisplay;
    private Spinner spinnerGameOption;
    private int gameOption = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_delete_alarm);

        firebaseHelper = new FirebaseHelper();
//        dbAlarms = FirebaseDatabase.getInstance().getReference("alarms");



        viewTitle = getIntent().getExtras().getString("ViewTitle");
        buttonName = getIntent().getExtras().getString("ButtonName");
        updateViewDetails();

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
            try {
                ((TextView) findViewById(R.id.tv_time_display)).setText(prevAlarm.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //TODO: Update Spinner value
//        (Spinner) findViewById(R.id.input_spinner); // Update value of Spinner
        } else {
            fragment = new PersonalAlarmDetailsFragment("Alarm");
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_alarm_details, fragment);
        transaction.commit();

        setViewToInstanceVar();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.game_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGameOption.setAdapter(adapter);
        spinnerGameOption.setOnItemSelectedListener(this);
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
        if (item.getItemId() == R.menu.alarm_edit_menu) {
            deleteAlarm();
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void updateViewDetails() {
        getSupportActionBar().setTitle(viewTitle);
        ((Button) findViewById(R.id.btn_create_save_alarm)).setText(buttonName);
    }

    private void setViewToInstanceVar() {
        tvTimeDisplay = findViewById(R.id.tv_time_display);
        tvAlarmName = findViewById(R.id.tv_alarm_name);
        spinnerGameOption = findViewById(R.id.input_spinner);
    }

    public void deleteAlarm() {
        //TODO: perform delete alarm
    }

    public void updateAlarm() {
        //update alarm with existing key
//        firebaseHelper.updateAlarm(newAlarm, alarmKey);
    }

    public void addAlarm() {
//        String id = dbAlarms.push().getKey();

//        Alarm alarm = new Alarm("2019-12-30 23:37:51","AlarmSatu",true, true,  1);
//        Alarm newAlarm = new Alarm("2019-12-30 23:37:51","AlarmSatu",true, true,  1);
//        dbAlarms.child(id).setValue(newAlarm);
        firebaseHelper.addAlarm();
    }

    public void submitButtonOnClick(View view) {
        //TODO: Save details in local database
        String time = (String) tvTimeDisplay.getText();
        String alarmName = (String) ((TextView)findViewById(R.id.tv_alarm_name)).getText();
        if (viewTitle.contains("Edit")) {
            newAlarm = new Alarm(time, alarmName, prevAlarm.isOn(), prevAlarm.isGroup(), prevAlarm.getGame());
            updateAlarm();
        } else if (viewTitle.contains("Personal")) {
            newAlarm = new Alarm(time, alarmName, true, false, gameOption);
            addAlarm();
        } else {
            newAlarm = new Alarm(time, alarmName, true, true, gameOption);
            addAlarm();
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
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        // assume gameOption = [NONE, TICTACTOE, MATH, SHAKER]
        gameOption = position;
        //TODO: For testing purpose only
        Toast.makeText(adapterView.getContext(), Integer.toString(gameOption) + adapterView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //do nothing
    }
}