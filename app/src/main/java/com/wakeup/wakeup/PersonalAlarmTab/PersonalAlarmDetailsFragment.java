package com.wakeup.wakeup.PersonalAlarmTab;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wakeup.wakeup.DialogWithTitle;
import com.wakeup.wakeup.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalAlarmDetailsFragment extends Fragment {
    private String alarmName;
    public PersonalAlarmDetailsFragment(String alarmName) {
        this.alarmName = alarmName;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_alarm_details, container, false);
        View location = (View) view.findViewById(R.id.layout_row_alarm_name);
        ((TextView) view.findViewById(R.id.tv_alarm_name)).setText(alarmName);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("S");
                openChangePersonalAlarmNameDialog(v);
            }
        });
        return view;
    }

    public void openChangePersonalAlarmNameDialog(View view) {
        DialogWithTitle changeAlarmNameDialog = new DialogWithTitle();
        Bundle args = new Bundle();
        args.putString("DialogTitle", "Alarm Name");
        args.putString("ValidButton", "SAVE");
        args.putString("InvalidButton", "DISCARD");
        changeAlarmNameDialog.setArguments(args);
        changeAlarmNameDialog.show(getActivity().getSupportFragmentManager(), "change alarm name dialog");
    }


}
