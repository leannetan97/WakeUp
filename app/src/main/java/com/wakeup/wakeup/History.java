package com.wakeup.wakeup;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

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

        getEntries();

        barDataSet = new BarDataSet(barEntries, "Minute");
        barData = new BarData(barDataSet);


        barChart.setData(barData);

    }

    private void getEntries() {
        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 4f));
        barEntries.add(new BarEntry(2, 3f));
        barEntries.add(new BarEntry(3,8f));
        barEntries.add(new BarEntry(4, 6f));
        barEntries.add(new BarEntry(5, 9f));
        barEntries.add(new BarEntry(6, 10f));
        barEntries.add(new BarEntry(7, 6f));
    }




}
