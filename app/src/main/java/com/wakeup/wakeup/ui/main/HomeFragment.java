package com.wakeup.wakeup.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    DatabaseReference dbAlarms;
    private RecyclerView.Adapter homeAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rvAlarm;

    private List<Alarm> alarms;


    public HomeFragment() {
        // Required empty public constructor
        alarms = new ArrayList<>();
        createDummyData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dbAlarms = FirebaseDatabase.getInstance().getReference("alarms");

        rvAlarm= view.findViewById(R.id.rv_home);
        layoutManager = new LinearLayoutManager(getContext());
        rvAlarm.setLayoutManager(layoutManager);
        homeAdapter = new HomeFragmentAdapter(alarms);
        rvAlarm.setAdapter(homeAdapter);

//        alarms.add(new Alarm("2019-12-30 23:37:50", "aname", true, 2));
//        PersonalAlarmAdapter personalAlarmAdapter = new PersonalAlarmAdapter(getContext(), R.layout.res_alarm_card_view, alarms);
//        lvAlarm.setAdapter(personalAlarmAdapter);
        return view;
    }

    private void createDummyData() {
        alarms.add(new Alarm("02:00","Alarm Name 1", true, false,2));
        alarms.add(new Alarm("03:00","Alarm Name 2", false, true,2));
        alarms.add(new Alarm("04:00","Alarm Name 3", true, true,2));
        alarms.add(new Alarm("05:00","Alarm Name 4", false, false,2));
        alarms.add(new Alarm("06:00","Alarm Name 5", true, true,2));
        alarms.add(new Alarm("07:00","Alarm Name 6", false, true,2));
        alarms.add(new Alarm("08:00","Alarm Name 7", true, false,2));
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        dbAlarms.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // clear previous list
//                alarms.clear();
//
//                //iterate through all nodes
//                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    // get alarms
//                    Alarm alarm = postSnapshot.getValue(Alarm.class);
//                    // add to list
//                    alarms.add(alarm);
//                }
//
//                // create adapter
//                PersonalAlarmAdapter personalAlarmAdapter = new PersonalAlarmAdapter(getContext(), R.layout.res_alarm_card_view, alarms);
//                lvAlarm.setAdapter(personalAlarmAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }


}

