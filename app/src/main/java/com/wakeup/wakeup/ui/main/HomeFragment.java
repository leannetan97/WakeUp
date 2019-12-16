package com.wakeup.wakeup.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    // firebase
    DatabaseReference dbAlarms;
    private List<Alarm> alarms;

    // adapter
    private RecyclerView.Adapter homeAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rvAlarm;


    public HomeFragment() {
        // Required empty public constructor
        alarms = new ArrayList<>();

        // firebase
        dbAlarms = new FirebaseHelper().getDbUserAlarms();
        // createDummyData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvAlarm= view.findViewById(R.id.rv_home);
        layoutManager = new LinearLayoutManager(getContext());
        rvAlarm.setLayoutManager(layoutManager);
        homeAdapter = new HomeFragmentAdapter(alarms);
        rvAlarm.setAdapter(homeAdapter);

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

                    alarms.add(alarm);
                }

                // create adapter
                homeAdapter = new HomeFragmentAdapter(alarms);
                rvAlarm.setAdapter(homeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}

