package com.wakeup.wakeup.ui.main;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wakeup.wakeup.HistoryTab.HistoryAdapter;
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.ObjectClass.Group;
import com.wakeup.wakeup.ObjectClass.History;
import com.wakeup.wakeup.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {
    // firebase
    private DatabaseReference dbUserGroups;
    private List<Group> groups;

    // adapter
    private RecyclerView rvGroup;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter groupAdapter;

    public GroupFragment() {
        // Required empty public constructor
        groups = new ArrayList<>();

        // firebase
        dbUserGroups = new FirebaseHelper().getDbUserGroups();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        createDummyData();

        rvGroup = view.findViewById(R.id.rv_group);
        layoutManager = new LinearLayoutManager(getContext());
        rvGroup.setLayoutManager(layoutManager);
        groupAdapter = new GroupFragmentAdapter(groups);
        rvGroup.setAdapter(groupAdapter);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        dbUserGroups.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // clear previous list
                groups.clear();

                // iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Group group = postSnapshot.getValue(Group.class);
//                    String groupKey = postSnapshot.getKey(); //alarm key
//                    group.setGroupKey(groupKey);
//
//                    groups.add(group);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    private void createDummyData() {
        Group temp = new Group("Group A");
        groups.add(temp);
        createDummyAlarmData(temp);
        temp = new Group("Group B");
        groups.add(temp);
        createDummyAlarmData(temp);
        temp = new Group("Group C");
        groups.add(temp);
        createDummyAlarmData(temp);
        temp = new Group("Group D");
        groups.add(temp);
        createDummyAlarmData(temp);
        temp = new Group("Group E");
        groups.add(temp);
        temp = new Group("Group F");
        groups.add(temp);
        createDummyAlarmData(temp);
    }

    private void createDummyAlarmData(Group group){
        group.addAlarm(new Alarm("02:00","Alarm Name 1", true, true,2));
        group.addAlarm(new Alarm("07:00","Alarm Name 2", false, true,2));
        group.addAlarm(new Alarm("13:00","Alarm Name 3", true, true,2));
        group.addAlarm(new Alarm("22:00","Alarm Name 4", false, true,2));
        group.addAlarm(new Alarm("23:00","Alarm Name 5", true, true,2));
    }
}

