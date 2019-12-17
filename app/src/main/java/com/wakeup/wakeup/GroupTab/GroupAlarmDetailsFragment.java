package com.wakeup.wakeup.GroupTab;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wakeup.wakeup.AwakeStatusListActivity;
import com.wakeup.wakeup.DialogWithTitle;
import com.wakeup.wakeup.ObjectClass.Friend;
import com.wakeup.wakeup.PersonalAlarmTab.PersonalAlarmDetailsFragment;
import com.wakeup.wakeup.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupAlarmDetailsFragment extends Fragment implements DialogWithTitle.DialogListener {
    private String alarmName;
    private TextView tvAlarmName;
    private ArrayList<Friend> allContacts;
    String groupKey;

    public GroupAlarmDetailsFragment(String alarmName) {
        this.alarmName = alarmName;
    }

    public GroupAlarmDetailsFragment(String alarmName, ArrayList<Friend> allContacts, String groupKey) {
        this.alarmName = alarmName;
        this.allContacts = allContacts;
        this.groupKey = groupKey;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_alarm_details, container, false);
        View alarmNameRow = view.findViewById(R.id.layout_row_alarm_name);
        View awakeStatueRow = view.findViewById(R.id.layout_row_awake_status);
        tvAlarmName = view.findViewById(R.id.tv_alarm_name);
        tvAlarmName.setText(alarmName);
        alarmNameRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangeGroupAlarmNameDialog(v);
            }
        });
        awakeStatueRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAwakeStatusActivity(v);
            }
        });
        return view;
    }

    private void openChangeGroupAlarmNameDialog(View view) {
        DialogWithTitle changeAlarmNameDialog = new DialogWithTitle();
        Bundle args = new Bundle();
        args.putString("DialogTitle", "Alarm Name");
        args.putString("Hint","Group Alarm Name");
        args.putString("ValidButton", "OK");
        args.putString("InvalidButton", "CANCEL");
        changeAlarmNameDialog.setTargetFragment(GroupAlarmDetailsFragment.this, 2);
        changeAlarmNameDialog.setArguments(args);
        changeAlarmNameDialog.show(getFragmentManager(), getString(R.string.change_alarm_name_dialog));
    }

    private void navigateToAwakeStatusActivity(View view){
        Intent toAwakeStatus = new Intent(view.getContext(), AwakeStatusListActivity.class);
        toAwakeStatus.putParcelableArrayListExtra("AllContacts", allContacts);
        toAwakeStatus.putExtra("GroupKey", groupKey);
        startActivity(toAwakeStatus);
    }

    @Override
    public void applyTexts(String newName) {
        if(newName.length() == 0){
            Toast.makeText(getActivity(),"Name cannot be empty. Please try again.",Toast.LENGTH_SHORT).show();
        }else {
            alarmName = newName;
            tvAlarmName.setText(alarmName);
        }
    }
}
