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

// NOTE: This adapter is Shared by SingleGroupActivity and AlarmFragment

public class PersonalGroupAlarmFragmentAdapter extends RecyclerView.Adapter<PersonalGroupAlarmFragmentAdapter.AlarmFragmentViewHolder>{
    private List<Alarm> alarms;

    public PersonalGroupAlarmFragmentAdapter(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    @NonNull
    @Override
    public AlarmFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.res_alarm_card_view, parent,false);
        return new AlarmFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmFragmentViewHolder holder, int position) {
        Alarm alarmItem = alarms.get(position);
        try {
            holder.tvTimeDisplay.setText(alarmItem.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

//public class PersonalAlarmAdapter extends ArrayAdapter<Alarm> {
//
//    private int resourceLayout;
//    private Context mContext;
//    private List<Alarm> personalAlarmArrayList;
//
//    private SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm");
//
//    public PersonalAlarmAdapter(@NonNull Context context, int resource, @NonNull List<Alarm> objects) {
//        super(context, resource, objects);
//        this.resourceLayout = resource;
//        this.mContext = context;
//        this.personalAlarmArrayList = objects;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View v = convertView;
//        if (v == null) {
//            LayoutInflater vi;
//            vi = LayoutInflater.from(mContext);
//            v = vi.inflate(resourceLayout, null);
//        }
//
//        // inflate each item
//        Alarm p = getItem(position);
//        if (p != null) {
//            TextView timeDisplay = (TextView) v.findViewById(R.id.tv_time_display);
//            TextView alarmName = (TextView) v.findViewById(R.id.tv_alarm_name);
//            ImageView cardCategoryIcon = (ImageView) v.findViewById(R.id.iv_alarm_category);
//
//            // time display
//            if (timeDisplay != null) {
//                Date date = null;
//                String dateStr = p.getTime();
////                    date = p.getTimeinDate();
////                    String dateStr = dateFormatter.format(date);
//                timeDisplay.setText(dateStr);
//            }
//            if (alarmName != null) {
//                alarmName.setText(p.getAlarmName());
//            }
//            if(p.isGroup()) {
//                cardCategoryIcon.setImageResource(R.drawable.ic_group_black_24dp);
//                cardCategoryIcon.setVisibility(View.VISIBLE);
//            }else {
//                cardCategoryIcon.setImageResource(R.drawable.ic_person_black_24dp);
//                cardCategoryIcon.setVisibility(View.VISIBLE);
//            }
//            //
//
//        }
//
//
//        return v;
//    }
//}