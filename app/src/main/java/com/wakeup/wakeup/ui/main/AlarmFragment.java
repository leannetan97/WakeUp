package com.wakeup.wakeup.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {

    // firebase
    DatabaseReference dbAlarms;
    private List<Alarm> alarms;

    // Adapter
    private RecyclerView rvAlarm;
    private RecyclerView.Adapter personalAlarmAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public AlarmFragment() {
        // Required empty public constructor
        alarms = new ArrayList<>();

        // firebase
        dbAlarms = new FirebaseHelper().getDbUserAlarms();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        rvAlarm = view.findViewById(R.id.rv_alarm);

        layoutManager = new LinearLayoutManager(getContext());
        rvAlarm.setLayoutManager(layoutManager);
        personalAlarmAdapter = new PersonalGroupAlarmFragmentAdapter(alarms);
        rvAlarm.setAdapter(personalAlarmAdapter);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        dbAlarms.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // clear previous list
                alarms.clear();

                // iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Alarm alarm = postSnapshot.getValue(Alarm.class);
                    String alarmKey = postSnapshot.getKey(); //alarm key
                    alarm.setAlarmKey(alarmKey);
                    if (!alarm.isGroup()) {
                        alarms.add(alarm);
                    }
                }

                // create adapter
//                personalAlarmAdapter = new HomeFragmentAdapter(alarms,);
                personalAlarmAdapter = new PersonalGroupAlarmFragmentAdapter(alarms);
                rvAlarm.setAdapter(personalAlarmAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
