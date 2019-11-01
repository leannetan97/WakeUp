package com.wakeup.wakeup.ui.main;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wakeup.wakeup.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {


    private ListView lvAlarm;
    private ArrayList<String> alarmTime = new ArrayList<>();
    private ArrayList<String> alarmName = new ArrayList<>();
    private ArrayList<Boolean> alarmIsOn = new ArrayList<>();

    public AlarmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        lvAlarm = view.findViewById(R.id.lv_alarm);
        createDummyData();
        AlarmFragment.AlarmListViewAdapter alarmCardListAdapter = new AlarmFragment.AlarmListViewAdapter(getContext(),alarmTime,alarmName,alarmIsOn);
        lvAlarm.setAdapter(alarmCardListAdapter);
        return view;

    }

    private void createDummyData() {
        alarmTime.add("06:00");
        alarmTime.add("07:00");
        alarmTime.add("08:00");
        alarmTime.add("08:10");
        alarmTime.add("09:00");
        alarmTime.add("10:00");
        alarmTime.add("20:00");

        alarmName.add("Alarm 1");
        alarmName.add("Alarm 2");
        alarmName.add("Alarm 3");
        alarmName.add("Alarm 4");
        alarmName.add("Alarm 5");
        alarmName.add("Alarm 1");
        alarmName.add("Alarm 1");

        alarmIsOn.add(true);
        alarmIsOn.add(true);
        alarmIsOn.add(true);
        alarmIsOn.add(true);
        alarmIsOn.add(true);
        alarmIsOn.add(true);
        alarmIsOn.add(true);
    }

    class AlarmListViewAdapter extends ArrayAdapter<String> {
        Context context;
        private ArrayList<String> alarmTime;
        private ArrayList<String> alarmName;
        private ArrayList<Boolean> alarmIsOn;

        AlarmListViewAdapter(Context c, ArrayList<String> alarmTime, ArrayList<String> alarmName,
                             ArrayList<Boolean> alarmIsOn) {
            super(c, R.layout.res_alarm_card_view, R.id.tv_time_display,alarmTime);
            this.context = c;
            this.alarmTime = alarmTime;
            this.alarmName = alarmName;
            this.alarmIsOn = alarmIsOn;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View card = inflater.inflate(R.layout.res_alarm_card_view,parent,false);
            ((TextView)card.findViewById(R.id.tv_time_display)).setText(alarmTime.get(position));

            ((TextView)card.findViewById(R.id.tv_alarm_name)).setText(alarmName.get(position));
            ((ImageView)card.findViewById(R.id.iv_alarm_category)).setVisibility(View.GONE);
            return card;
        }
    }

}
