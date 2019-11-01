package com.wakeup.wakeup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SingleGroupActivity extends AppCompatActivity {


    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_group);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Group Name X");

        lv = (ListView) findViewById(R.id.lv_group_cards);

        ArrayList<String> alNames = new ArrayList<>();
        alNames.add("Name 1");
        alNames.add("Name 2");
        alNames.add("Name 3");
        alNames.add("Name 4");
        alNames.add("Name 5");
        alNames.add("Name 6");

        ArrayList<String> alTimes = new ArrayList<>();
        alTimes.add("06:00");
        alTimes.add("21:00");
        alTimes.add("05:00");
        alTimes.add("08:00");
        alTimes.add("10:00");
        alTimes.add("08:00");


        SingleGroupActivity.CustomAdapter customAdapter =
                new SingleGroupActivity.CustomAdapter(this, alTimes, alNames);

        lv.setAdapter(customAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.single_group_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_groupSettings:
                Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SingleGroupActivity.this, GroupSettingsFriendsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }


    class CustomAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> alarmTimes;
        ArrayList<String> alarmNames;


        CustomAdapter(Context c, ArrayList<String> alarmTimes, ArrayList<String> alarmNames) {
            super(c, R.layout.res_alarm_card_view, R.id.tv_time_display, alarmTimes);
            this.context = c;
            this.alarmTimes = alarmTimes;
            this.alarmNames = alarmNames;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.res_alarm_card_view, parent, false);

            TextView tvTimeDisplay = row.findViewById(R.id.tv_time_display);
            TextView tvAlarmName = row.findViewById(R.id.tv_alarm_name);
            Switch tbtnAlarm = row.findViewById(R.id.tbtn_alarm);

            tvTimeDisplay.setText(alarmTimes.get(position));
            tvAlarmName.setText(alarmNames.get(position));

            tbtnAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(context, "Toggled On: " + isChecked, Toast.LENGTH_SHORT);
                }
            });
//            ivBtnCall.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "call " + names.get(position), Toast.LENGTH_SHORT)
//                    .show();
//                }
//            });

            return row;
        }
    }
}
