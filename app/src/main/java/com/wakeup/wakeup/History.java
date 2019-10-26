package com.wakeup.wakeup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//https://openclassrooms.com/en/courses/5086986-create-a-scalable-and-powerful-backend-for-android-using-firebase-in-java/5769271-integrate-firebase-into-an-android-app

public class History extends AppCompatActivity {

    // format
    private DecimalFormat mFormat;

    // bar chart
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;

    // history list
    String[] stringArray;
    ArrayList histEntries;
    private SimpleDateFormat dateFormatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        barChart = findViewById(R.id.historyChart);
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
        int startColor = ContextCompat.getColor(this, android.R.color.holo_blue_light);
        int endColor = ContextCompat.getColor(this, android.R.color.holo_purple);
        barDataSet.setGradientColor(startColor, endColor);
        barDataSet.setValueFormatter(new DefaultValueFormatter(0));
        barDataSet.setValueTextSize(10);

        barData = new BarData(barDataSet);
        barData.setBarWidth(0.4f); // set bar width

        barChart.setData(barData);


//        stringArray =  new String[] {"Android","IPhone","WindowsMobile","Blackberry", "WebOS","Ubuntu","Windows7","Max OS X"};
//
////        ArrayList<String> list = new ArrayList<>();
////        for (int i=0; i<stringArray.length; ++i) {
////            list.add(stringArray[i]);
////        }
//
//
//        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_historyview, stringArray);

        ListView listView = (ListView)findViewById(R.id.historyList);

        try {
            getHistList();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        HistoryAdapter historyAdapter = new HistoryAdapter(this, R.layout.activity_historyview, histEntries);

        listView.setAdapter(historyAdapter);
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

    private void getHistList() throws ParseException {

        Date date1 = dateFormatter.parse("2019-12-30 23:37:50");
        Date date2 = dateFormatter.parse("2019-12-30 03:37:50");
        Date date3 = dateFormatter.parse("2019-12-30 13:37:50");

        histEntries = new ArrayList<HistoryModel>();
        histEntries.add(new HistoryModel(date1, 6));
        histEntries.add(new HistoryModel(date2, 4));
        histEntries.add(new HistoryModel(date3, 5));
    }

}
