package com.wakeup.wakeup.ui.main;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.wakeup.wakeup.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ListView lvAlarm;
    private ArrayList<String> alarmTime = new ArrayList<>();
    private ArrayList<String> alarmName = new ArrayList<>();
    private ArrayList<Boolean> alarmIsOn = new ArrayList<>();
    private ArrayList<Boolean> alarmIsGroup = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lvAlarm = view.findViewById(R.id.lv_home);
        createDummyData();
        HomeFragment.AlarmListViewAdapter homeAlarmCardListAdapter =
                new HomeFragment.AlarmListViewAdapter(getContext(), alarmTime, alarmName,
                        alarmIsOn, alarmIsGroup);
        lvAlarm.setAdapter(homeAlarmCardListAdapter);
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

        alarmIsGroup.add(true);
        alarmIsGroup.add(false);
        alarmIsGroup.add(true);
        alarmIsGroup.add(false);
        alarmIsGroup.add(true);
        alarmIsGroup.add(false);
        alarmIsGroup.add(true);
    }

    class AlarmListViewAdapter extends ArrayAdapter<String> {
        Context context;
        private ArrayList<String> alarmTime;
        private ArrayList<String> alarmName;
        private ArrayList<Boolean> alarmIsOn;
        private ArrayList<Boolean> alarmIsGroup = new ArrayList<>();

        AlarmListViewAdapter(Context c, ArrayList<String> alarmTime, ArrayList<String> alarmName,
                             ArrayList<Boolean> alarmIsOn,ArrayList<Boolean> alarmIsGroup) {
            super(c, R.layout.res_alarm_card_view, R.id.tv_time_display, alarmTime);
            this.context = c;
            this.alarmTime = alarmTime;
            this.alarmName = alarmName;
            this.alarmIsOn = alarmIsOn;
            this.alarmIsGroup = alarmIsGroup;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            LayoutInflater inflater =
                    (LayoutInflater) getActivity().getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View card = inflater.inflate(R.layout.res_alarm_card_view, parent, false);
            ((TextView) card.findViewById(R.id.tv_time_display)).setText(alarmTime.get(position));

            ((TextView) card.findViewById(R.id.tv_alarm_name)).setText(alarmName.get(position));
            ImageView cardCategoryIcon = (ImageView) card.findViewById(R.id.iv_alarm_category);
            if(alarmIsGroup.get(position)) {
                cardCategoryIcon.setImageResource(R.drawable.ic_group_black_24dp);
                cardCategoryIcon.setVisibility(View.VISIBLE);
            }else {
                cardCategoryIcon.setImageResource(R.drawable.ic_person_black_24dp);
                cardCategoryIcon.setVisibility(View.VISIBLE);
            }
            return card;
        }
    }
}

