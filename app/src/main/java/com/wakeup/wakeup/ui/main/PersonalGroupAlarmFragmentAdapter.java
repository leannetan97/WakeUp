package com.wakeup.wakeup.ui.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wakeup.wakeup.AlarmReceiver;
import com.wakeup.wakeup.CreateDeleteAlarm;
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

// NOTE: This adapter is Shared by SingleGroupActivity and AlarmFragment

public class PersonalGroupAlarmFragmentAdapter extends RecyclerView.Adapter<PersonalGroupAlarmFragmentAdapter.AlarmFragmentViewHolder>{
    private List<Alarm> alarms;
    private FirebaseHelper firebaseHelper;
    private Context context;

    public PersonalGroupAlarmFragmentAdapter(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    @NonNull
    @Override
    public AlarmFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.res_alarm_card_view, parent,false);
        firebaseHelper = new FirebaseHelper();
        context = parent.getContext();
        return new AlarmFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmFragmentViewHolder holder, int position){
        Alarm alarmItem = alarms.get(position);
        holder.tvTimeDisplay.setText(alarmItem.getTime());
        holder.tvAlarmName.setText(alarmItem.getAlarmName());
        holder.ivIcon.setVisibility(View.INVISIBLE);
        if(alarmItem.isOn()){
            holder.tBtnAlarm.setChecked(true);
        }else {
            holder.tBtnAlarm.setChecked(false);
        }
        holder.rowParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToEditPersonalOrGroupAlarm(view,alarmItem);
            }
        });

        holder.tBtnAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleSwitch(buttonView, isChecked, alarmItem);
            }
        });
    }

    // handle alarm switch
    private void handleSwitch(CompoundButton buttonView, boolean isChecked, Alarm alarm){
        if(alarm.isOn()){
            //originally is set
            alarm.setOn(false);
            buttonView.setChecked(false);
            Toast.makeText(context,alarm.getTime() + " is OFF",Toast.LENGTH_SHORT).show();
        }else{
            // originally is not set
            alarm.setOn(true);
            buttonView.setChecked(true);
            Toast.makeText(context,alarm.getTime() + " is ON",Toast.LENGTH_SHORT).show();
        }
        updateAlarm(alarm);
    }
    
    public void updateAlarm(Alarm alarm) {
        //update alarm with existing key
        firebaseHelper.updateAlarm(alarm, alarm.getAlarmKey());
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    private void navigateToEditPersonalOrGroupAlarm(View view, Alarm alarmItem){
        Intent toEditorDeletePersonalOrGroupAlarmActivity = new Intent(view.getContext(), CreateDeleteAlarm.class);
        if(alarmItem.isGroup()){
            toEditorDeletePersonalOrGroupAlarmActivity.putExtra("ViewTitle", "Edit Group Alarm");
        }else{
            toEditorDeletePersonalOrGroupAlarmActivity.putExtra("ViewTitle", "Edit Personal Alarm");
        }
        toEditorDeletePersonalOrGroupAlarmActivity.putExtra("ButtonName", "Save Alarm");
        toEditorDeletePersonalOrGroupAlarmActivity.putExtra("AlarmData", alarmItem);
        view.getContext().startActivity(toEditorDeletePersonalOrGroupAlarmActivity);
    }


    public class AlarmFragmentViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTimeDisplay;
        private ImageView ivIcon;
        private TextView tvAlarmName;
        private Switch tBtnAlarm;
        private RelativeLayout rowParentLayout;

        public AlarmFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeDisplay = (TextView) itemView.findViewById(R.id.tv_time_display);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_alarm_category);
            tvAlarmName = (TextView) itemView.findViewById(R.id.tv_alarm_name);
            tBtnAlarm = (Switch) itemView.findViewById(R.id.tbtn_alarm);
            rowParentLayout = (RelativeLayout) itemView.findViewById(R.id.view_alarm_card);
        }
    }
}