package com.wakeup.wakeup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AwakeStatusListActivity extends AppCompatActivity {


    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awake_status_list);
        ActionBar tb = getSupportActionBar();
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Awake Status List");

        lv = (ListView) findViewById(R.id.lv_awakeStatusList);

        ArrayList<String> alName = new ArrayList<>();
        alName.add("friend1");
        alName.add("friend2");
        alName.add("friend3");

        ArrayList<Boolean> alStatus = new ArrayList<>();

        alStatus.add(false);
        alStatus.add(true);
        alStatus.add(true);

        ArrayList<String> alEmail= new ArrayList<>();

        alEmail.add("abc@bmail.com");
        alEmail.add("def@email.fom");
        alEmail.add("ghi@hmail.iom");

        CustomAdapter customAdapter = new CustomAdapter(this, alName, alStatus, alEmail);

        lv.setAdapter(customAdapter);

//        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, al);
//        lv.setAdapter(ad);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(view.getContext(),
//                        "Clicked: " + al.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    class CustomAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> names;
        ArrayList<String> emails;
        ArrayList<Boolean> awakeStatus;
//        String descriptions[];

        CustomAdapter(Context c, ArrayList<String> names, ArrayList<Boolean> awakeStatus, ArrayList<String> emails) {
            super(c, R.layout.layout_row_awake_status_list, R.id.tv_friendName, names);
            this.context = c;
            this.names = names;
            this.awakeStatus = awakeStatus;
            this.emails = emails;
//            this.descriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.layout_row_awake_status_list, parent, false);

            TextView tvFriendName = row.findViewById(R.id.tv_friendName);
            tvFriendName.setText(names.get(position));

            TextView tvFriendEmail = row.findViewById(R.id.tv_friendEmail);
            tvFriendEmail.setText(emails.get(position));

            ImageView ivAwakeStatus = row.findViewById(R.id.iv_awakeStatus);
            ImageView btnCall = row.findViewById(R.id.btn_call);
            if(awakeStatus.get(position)) {
                ivAwakeStatus.setImageResource(R.drawable.ic_awake_green);
            }else{
                ivAwakeStatus.setImageResource(R.drawable.ic_sleep_red);
            }
//            btnCall.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorAccent));
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "call " + names.get(position), Toast.LENGTH_SHORT).show();
                }
            });
//            System.out.println(titles.get(position));



            return row;
        }
    }
}
