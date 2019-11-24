package com.wakeup.wakeup.ui.main;

import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wakeup.wakeup.CreateDeleteAlarm;
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.R;

import java.text.ParseException;
import java.util.List;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.HomeFragmentViewHolder> {
    private List<Alarm> alarms;

    public HomeFragmentAdapter(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    @NonNull
    @Override
    public HomeFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.res_alarm_card_view, parent,false);
        return new HomeFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragmentViewHolder holder, int position) {
        Alarm alarmItem = alarms.get(position);
        try {
            holder.tvTimeDisplay.setText(alarmItem.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvAlarmName.setText(alarmItem.getAlarmName());
        if(alarmItem.isGroup()) {
            holder.ivIcon.setImageResource(R.drawable.ic_group_black_24dp);
        }else {
            holder.ivIcon.setImageResource(R.drawable.ic_person_black_24dp);
        }
        holder.ivIcon.setVisibility(View.VISIBLE);
        if(alarmItem.isOn()){
            holder.tBtnAlarm.setChecked(true);
        }else {
            holder.tBtnAlarm.setChecked(false);
        }

        holder.rowParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToEditAlarm(view,alarmItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }


    private void navigateToEditAlarm(View view,Alarm alarmItem){
        Intent toEditAlarmActivity = new Intent(view.getContext(), CreateDeleteAlarm.class);
        toEditAlarmActivity.putExtra("ViewTitle", "Edit Alarm");
        toEditAlarmActivity.putExtra("ButtonName", "Save Alarm");
        toEditAlarmActivity.putExtra("AlarmData", alarmItem);
        view.getContext().startActivity(toEditAlarmActivity);
    }

    public class HomeFragmentViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTimeDisplay;
        private ImageView ivIcon;
        private TextView tvAlarmName;
        private Switch tBtnAlarm;
        private RelativeLayout rowParentLayout;

        public HomeFragmentViewHolder(View itemView) {
            super(itemView);
            tvTimeDisplay = (TextView) itemView.findViewById(R.id.tv_time_display);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_alarm_category);
            tvAlarmName = (TextView) itemView.findViewById(R.id.tv_alarm_name);
            tBtnAlarm = (Switch) itemView.findViewById(R.id.tbtn_alarm);
            rowParentLayout = (RelativeLayout) itemView.findViewById(R.id.view_alarm_card);
        }
    }

}
