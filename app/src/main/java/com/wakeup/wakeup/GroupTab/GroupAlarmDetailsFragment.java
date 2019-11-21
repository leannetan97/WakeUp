package com.wakeup.wakeup.GroupTab;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wakeup.wakeup.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupAlarmDetailsFragment extends Fragment {
    private String alarmName;
    public GroupAlarmDetailsFragment(String alarmName) {
        this.alarmName = alarmName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_alarm_details, container, false);
        // Inflate the layout for this fragment
        ((TextView) view.findViewById(R.id.tv_alarm_name)).setText(alarmName);
        return view;
    }

}
