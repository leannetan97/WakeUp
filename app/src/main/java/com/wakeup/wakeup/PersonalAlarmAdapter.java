package com.wakeup.wakeup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wakeup.wakeup.ObjectClass.Alarm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PersonalAlarmAdapter extends ArrayAdapter<Alarm> {

    private int resourceLayout;
    private Context mContext;
    private List<Alarm> personalAlarmArrayList;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm");

    public PersonalAlarmAdapter(@NonNull Context context, int resource, @NonNull List<Alarm> objects) {
        super(context, resource, objects);
        this.resourceLayout = resource;
        this.mContext = context;
        this.personalAlarmArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        // inflate each item
        Alarm p = getItem(position);
        if (p != null) {
            TextView timeDisplay = (TextView) v.findViewById(R.id.tv_time_display);
            TextView alarmName = (TextView) v.findViewById(R.id.tv_alarm_name);
            ImageView cardCategoryIcon = (ImageView) v.findViewById(R.id.iv_alarm_category);

            // time display
            if (timeDisplay != null) {
                Date date = null;
                String dateStr = p.getTime();
//                    date = p.getTimeinDate();
//                    String dateStr = dateFormatter.format(date);
                timeDisplay.setText(dateStr);
            }
            if (alarmName != null) {
                alarmName.setText(p.getAlarmName());
            }
            if(p.isGroup()) {
                cardCategoryIcon.setImageResource(R.drawable.ic_group_black_24dp);
                cardCategoryIcon.setVisibility(View.VISIBLE);
            }else {
                cardCategoryIcon.setImageResource(R.drawable.ic_person_black_24dp);
                cardCategoryIcon.setVisibility(View.VISIBLE);
            }
            //

        }


        return v;
    }
}
