package com.wakeup.wakeup.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {

    private RecyclerView rvAlarm;
    private RecyclerView.Adapter personalAlarmAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Alarm> alarms;

    public AlarmFragment() {
        // Required empty public constructor
        alarms = new ArrayList<>();
        createDummyData();
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

    private void createDummyData() {
        alarms.add(new Alarm("02:00","Alarm Name 1", true, false,2));
        alarms.add(new Alarm("03:00","Alarm Name 2", false, false,2));
        alarms.add(new Alarm("04:00","Alarm Name 3", true, false,2));
        alarms.add(new Alarm("05:00","Alarm Name 4", false, false,2));
        alarms.add(new Alarm("06:00","Alarm Name 5", true, false,2));
        alarms.add(new Alarm("07:00","Alarm Name 6", false, false,2));
        alarms.add(new Alarm("08:00","Alarm Name 7", true, false,2));
    }

}
