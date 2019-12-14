package com.wakeup.wakeup.ui.main;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    // firebase
    DatabaseReference dbUserHistory;
    private List<History> histories;

    // adaptor
    private HistoryAdapter historyAdapter;
    private ListView lvHistory;



    // history list
    private SimpleDateFormat dateFormatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // format
    private DecimalFormat mFormat;


    // bar chart
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;


    public HistoryFragment() {
        // Required empty public constructor
        histories = new ArrayList<>();

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

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0);
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = barChart.getLegend();
        l.setEnabled(false);

        // chart data
        getEntries();

        barDataSet = new BarDataSet(barEntries, "Minute");


        // set chart gradient color
        int startColor = ContextCompat.getColor(getContext(), android.R.color.holo_blue_light);
        int endColor = ContextCompat.getColor(getContext(), android.R.color.holo_purple);
        barDataSet.setGradientColor(startColor, endColor);
        barDataSet.setValueFormatter(new DefaultValueFormatter(0));
        barDataSet.setValueTextSize(10);

        barData = new BarData(barDataSet);
        barData.setBarWidth(0.4f); // set bar width

        barChart.setData(barData);


        // List
        lvHistory = (ListView) view.findViewById(R.id.historyList);

//        try {
//            getHistList();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        historyAdapter = new HistoryAdapter(getContext(), R.layout.activity_history_view, histories);
//
//        lvHistory.setAdapter(historyAdapter);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }






    private void getEntries() {
        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 4));
        barEntries.add(new BarEntry(2, 3));
        barEntries.add(new BarEntry(3,8));
        barEntries.add(new BarEntry(4, 6));
        barEntries.add(new BarEntry(5, 9));
        barEntries.add(new BarEntry(6, 10));
        barEntries.add(new BarEntry(7, 6));
    }

//    private void getHistList() throws ParseException {
//
//        Date date1 = dateFormatter.parse("2019-12-30 23:37:50");
//        Date date2 = dateFormatter.parse("2019-12-30 03:37:50");
//        Date date3 = dateFormatter.parse("2019-12-30 13:37:50");
//
//        histories = new ArrayList<History>();
//        HashMap hashMap = new HashMap<String, String>();
//        hashMap.put("timestamp", "2019-12-30 23:37:50");
//
//        histories.add(new History(6, hashMap));
//
////        histories.add(new History(date2, 4));
////        histories.add(new History(date3, 5));
////        histories.add(new History(date1, 6));
////        histories.add(new History(date2, 4));
////        histories.add(new History(date3, 5));
////        histories.add(new History(date1, 6));
////        histories.add(new History(date2, 4));
////        histories.add(new History(date3, 5));
////        histories.add(new History(date1, 6));
////        histories.add(new History(date2, 4));
////        histories.add(new History(date3, 5));
////        histories.add(new History(date1, 6));
////        histories.add(new History(date2, 4));
////        histories.add(new History(date3, 5));
//    }

    public void initBarChart() {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.animateX(500);

        // define chart
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0);
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = barChart.getLegend();
        l.setEnabled(false);
    }

}
