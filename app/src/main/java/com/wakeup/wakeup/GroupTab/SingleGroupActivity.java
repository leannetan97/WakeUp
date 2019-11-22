package com.wakeup.wakeup.GroupTab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.wakeup.wakeup.CreateDeleteAlarm;
import com.wakeup.wakeup.Home;
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.R;
import com.wakeup.wakeup.ui.main.PersonalGroupAlarmFragmentAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SingleGroupActivity extends AppCompatActivity {

    private String groupName;

    private RecyclerView rvGroupAlarm;
    private RecyclerView.Adapter groupAlarmAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Alarm> alarms;

    public SingleGroupActivity(){
        this.alarms = new ArrayList<>() ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_group);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fBtnGroupAlarm = findViewById(R.id.fbtn_group_alarm);
        fBtnGroupAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToCreateGroupAlarm(view);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        groupName = getIntent().getExtras().getString("GroupName");
        getSupportActionBar().setTitle(groupName);
        alarms = getIntent().getParcelableArrayListExtra("GroupAlarmsList");

        rvGroupAlarm = (RecyclerView) findViewById(R.id.rv_group_cards);
        layoutManager = new LinearLayoutManager(this);
        rvGroupAlarm.setLayoutManager(layoutManager);
        groupAlarmAdapter = new PersonalGroupAlarmFragmentAdapter(alarms);
        rvGroupAlarm.setAdapter(groupAlarmAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.single_group_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_groupSettings:
                Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SingleGroupActivity.this, GroupSettingsFriendsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
    // New Alarm
    private void navigateToCreateGroupAlarm(View view) {
        Intent alarmView = new Intent(this, CreateDeleteAlarm.class);
        alarmView.putExtra("ViewTitle", "New Group Alarm");
        alarmView.putExtra("ButtonName", "Create Alarm");
        startActivity(alarmView);
    }
}
