package com.wakeup.wakeup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.model.GradientColor;

import java.util.ArrayList;

//https://openclassrooms.com/en/courses/5086986-create-a-scalable-and-powerful-backend-for-android-using-firebase-in-java/5769271-integrate-firebase-into-an-android-app

public class History extends AppCompatActivity {

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        barChart = findViewById(R.id.historyChart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);

        // define chart
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = barChart.getLegend();
        l.setEnabled(false);

        // chart data
        getEntries();

        barDataSet = new BarDataSet(barEntries, "Minute");

        // set chart gradient
        int startColor = ContextCompat.getColor(this, android.R.color.holo_blue_light);
        int endColor = ContextCompat.getColor(this, android.R.color.holo_purple);
        barDataSet.setGradientColor(startColor, endColor);

        // set bar width
        barData = new BarData(barDataSet);
        barData.setBarWidth(0.4f);

        barChart.setData(barData);
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




}
