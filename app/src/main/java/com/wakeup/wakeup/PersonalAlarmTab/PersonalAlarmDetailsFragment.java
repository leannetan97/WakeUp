package com.wakeup.wakeup.PersonalAlarmTab;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wakeup.wakeup.DialogWithTitle;
import com.wakeup.wakeup.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalAlarmDetailsFragment extends Fragment implements DialogWithTitle.DialogListener {
    private String alarmName;
    private TextView tvAlarmName;
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
        View location = view.findViewById(R.id.layout_row_alarm_name);
        tvAlarmName = view.findViewById(R.id.tv_alarm_name);
        tvAlarmName.setText(alarmName);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangePersonalAlarmNameDialog(v);
            }
        });
        return view;
    }

    private void openChangePersonalAlarmNameDialog(View view) {
        DialogWithTitle changeAlarmNameDialog = new DialogWithTitle();
        Bundle args = new Bundle();
        args.putString("DialogTitle", "Alarm Name");
        args.putString("Hint","Alarm Name");
        args.putString("ValidButton", "OK");
        args.putString("InvalidButton", "CANCEL");
        changeAlarmNameDialog.setTargetFragment(PersonalAlarmDetailsFragment.this, 1);
        changeAlarmNameDialog.setArguments(args);
        changeAlarmNameDialog.show(getFragmentManager(), getString(R.string.change_alarm_name_dialog));
    }

    @Override
    public void applyTexts(String newName) {
        if(newName.length() == 0){
            Toast.makeText(getActivity(),"Name cannot be empty. Please try again.",Toast.LENGTH_SHORT).show();
        }else{
            alarmName = newName;
            tvAlarmName.setText(alarmName);
        }
    }
}
