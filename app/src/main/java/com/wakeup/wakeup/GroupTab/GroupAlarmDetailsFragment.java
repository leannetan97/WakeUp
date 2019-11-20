package com.wakeup.wakeup.GroupTab;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wakeup.wakeup.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupAlarmDetailsFragment extends Fragment {


    public GroupAlarmDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_alarm_details, container, false);
    }

}
