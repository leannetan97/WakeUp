package com.wakeup.wakeup.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    DatabaseReference dbAlarms;


    private ListView lvAlarm;
    private List<Alarm> alarms;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dbAlarms = FirebaseDatabase.getInstance().getReference("alarms");

//        lvAlarm= (ListView) view.findViewById(R.id.lv_home);

//        alarms.add(new Alarm("2019-12-30 23:37:50", "aname", true, 2));
//        PersonalAlarmAdapter personalAlarmAdapter = new PersonalAlarmAdapter(getContext(), R.layout.res_alarm_card_view, alarms);
//        lvAlarm.setAdapter(personalAlarmAdapter);
        return view;

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

