package com.wakeup.wakeup.GroupTab;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakeup.wakeup.CreateDeleteAlarm;
import com.wakeup.wakeup.Home;
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.ObjectClass.Friend;
import com.wakeup.wakeup.ObjectClass.Group;
import com.wakeup.wakeup.R;
import com.wakeup.wakeup.ui.main.PersonalGroupAlarmFragmentAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.provider.ContactsContract;
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
    private String groupKey;
    private Group group;

    private DatabaseReference dbGroupAlarms;

    private RecyclerView rvGroupAlarm;
    private RecyclerView.Adapter groupAlarmAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Alarm> alarms;

    private ArrayList<Friend> allContacts;

    public SingleGroupActivity() {
        this.alarms = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_group);
        allContacts = getIntent().getExtras().getParcelableArrayList("AllContacts");


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

        groupKey = getIntent().getExtras().getString("GroupKey");
        groupName = getIntent().getExtras().getString("GroupName");
        group = getIntent().getExtras().getParcelable("Group");
        getSupportActionBar().setTitle(groupName);



//        alarms = getIntent().getParcelableArrayListExtra("GroupAlarmsList");

    }

    @Override
    protected void onStart() {
        super.onStart();
        dbGroupAlarms = FirebaseDatabase.getInstance().getReference("groups").child(groupKey).child("alarms");
        dbGroupAlarms.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("yo");
                alarms.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    System.out.println("true");
                    Alarm alarm = postSnapshot.getValue(Alarm.class);
                    String alarmKey = postSnapshot.getKey();
                    alarm.setAlarmKey(alarmKey);
                    alarms.add(alarm);
                }

                if (alarms.size() > 0) {

                    TextView tvNoAlarm = findViewById(R.id.tv_no_alarms_yet);
                    tvNoAlarm.setVisibility(View.GONE);
                    rvGroupAlarm = (RecyclerView) findViewById(R.id.rv_group_cards);
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    rvGroupAlarm.setLayoutManager(layoutManager);
                    groupAlarmAdapter = new PersonalGroupAlarmFragmentAdapter(alarms);
                    rvGroupAlarm.setAdapter(groupAlarmAdapter);
                } else {
                    TextView tvNoAlarm = findViewById(R.id.tv_no_alarms_yet);
                    tvNoAlarm.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                Toast.makeText(this, "Loading Members...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SingleGroupActivity.this,
                        GroupSettingsFriendsActivity.class);
                intent.putExtra("GroupKey", groupKey);
//                intent.putExtra("Group", group);
                intent.putParcelableArrayListExtra("AllContacts", allContacts);
                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
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
        alarmView.putExtra("GroupKey", groupKey);
        alarmView.putExtra("Group", (Parcelable) group);
        startActivity(alarmView);
    }
}
