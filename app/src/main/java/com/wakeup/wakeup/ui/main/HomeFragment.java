package com.wakeup.wakeup.ui.main;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakeup.wakeup.AlarmReceiver;
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    // firebase
    DatabaseReference dbAlarms;
    private List<Alarm> alarms;

    // adapter
    private RecyclerView.Adapter homeAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rvAlarm;


    public HomeFragment() {
        // Required empty public constructor
        alarms = new ArrayList<>();

        // firebase
        dbAlarms = new FirebaseHelper().getDbUserAlarms();
        // createDummyData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvAlarm = view.findViewById(R.id.rv_home);
        layoutManager = new LinearLayoutManager(getContext());
        rvAlarm.setLayoutManager(layoutManager);
        homeAdapter = new HomeFragmentAdapter(alarms);
        rvAlarm.setAdapter(homeAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        dbAlarms.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // clear previous list
                alarms.clear();

                // iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Alarm alarm = postSnapshot.getValue(Alarm.class);
                    String alarmKey = postSnapshot.getKey(); //alarm key
                    alarm.setAlarmKey(alarmKey);
                    if (alarm.isOn()) {
                        try {
                            startAlarm(alarm);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        cancelAlarm(alarm);
                    }
                    alarms.add(alarm);
                }

                // create adapter
                homeAdapter = new HomeFragmentAdapter(alarms);
                rvAlarm.setAdapter(homeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void startAlarm(Alarm alarm) throws ParseException {
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        //Change the alarm object to byte so that pass
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
//        try {
        Bundle alarmBundle = new Bundle();
        alarmBundle.putParcelable("alarm", alarm);
        intent.putExtra("alarmBundle",alarmBundle);
//            out = new ObjectOutputStream(bos);
//            out.writeObject(alarm);
//            out.flush();
//            byte[] data = bos.toByteArray();
//            intent.putExtra("alarm", data);
        System.out.println("Data is store in byte:" + alarmBundle);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                bos.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }

        AlarmManager alarmManager =
                (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),
                (alarm.getAlarmKey()).hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar c = alarm.getTimeInCalender();

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }

    private void cancelAlarm(Alarm alarm) {
        AlarmManager alarmManager =
                (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),
                (alarm.getAlarmKey()).hashCode(), intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(getContext(), "Alarm is Cancel.", Toast.LENGTH_SHORT).show();
        }
    }
}

