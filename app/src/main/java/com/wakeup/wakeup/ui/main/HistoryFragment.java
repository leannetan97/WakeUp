package com.wakeup.wakeup.ui.main;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wakeup.wakeup.HistoryTab.HistoryAdapter;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.ObjectClass.History;
import com.wakeup.wakeup.R;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    // firebase
    DatabaseReference dbUserHistory;
    private List<History> histories;
    private List delays;
    private List<Integer> delayHisto;

    // adaptor
    private HistoryAdapter historyAdapter;
    private ListView lvHistory;

    // bar chart
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;


    public HistoryFragment() {
        // Required empty public constructor
        histories = new ArrayList<>();
        delays = new ArrayList<>();


        //firebase
        dbUserHistory = new FirebaseHelper().getDbUserHistory();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        barChart = view.findViewById(R.id.historyChart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.animateX(500);

        // define chart
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setEnabled(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0);
        leftAxis.setDrawGridLines(false);
        leftAxis.setEnabled(false);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = barChart.getLegend();
        l.setEnabled(false);

        // List
        lvHistory = (ListView) view.findViewById(R.id.historyList);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        dbUserHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // clear previous list
                histories.clear();

                // iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    int delay = postSnapshot.child("delay").getValue(Integer.class);
                    Long date = (Long) postSnapshot.child("timestamp").getValue();

                    histories.add(new History(delay, date));
                }

                // create adapter
                historyAdapter = new HistoryAdapter(getContext(), R.layout.activity_history_view, histories);
                lvHistory.setAdapter(historyAdapter);

                // chart data

                getEntries();

                barDataSet = new BarDataSet(delays, "Minute");


                // set chart gradient color
                int startColor = ContextCompat.getColor(getContext(), android.R.color.holo_blue_light);
                int endColor = ContextCompat.getColor(getContext(), android.R.color.holo_purple);
                barDataSet.setGradientColor(startColor, endColor);
                barDataSet.setValueFormatter(new DefaultValueFormatter(0));
                barDataSet.setValueTextSize(10);

                barData = new BarData(barDataSet);
                barData.setBarWidth(0.4f); // set bar width

                barChart.setData(barData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    private void getEntries() {
        delayHisto = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0));

        LocalDate curDate = LocalDate.now();

        for (History temp : histories) {
            Long dateLong = temp.getDate();
            LocalDate date = Instant.ofEpochMilli(dateLong).atZone(ZoneId.systemDefault()).toLocalDate();
            Long diff = ChronoUnit.DAYS.between(date, curDate);
            int dayDiff = 6 - diff.intValue();
            Log.d("histo", String.valueOf(dayDiff));

            if (dayDiff < delayHisto.size()) {
                int prev = delayHisto.get(dayDiff);
                delayHisto.set(dayDiff, (prev + temp.getDelay()));
            }
        }

        delays.clear();
        for (int i=0; i<delayHisto.size(); i++) {
            delays.add(new BarEntry(i+1, delayHisto.get(i)));
        }
    }

}
