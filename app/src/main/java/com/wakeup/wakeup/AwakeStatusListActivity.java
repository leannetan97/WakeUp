package com.wakeup.wakeup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wakeup.wakeup.ObjectClass.Friend;

import java.util.ArrayList;

public class AwakeStatusListActivity extends AppCompatActivity {

    private ArrayList<Friend> friends;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awake_status_list);
        ActionBar tb = getSupportActionBar();
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Awake Status List");

        lv = (ListView) findViewById(R.id.lv_awakeStatusList);


        friends = new ArrayList<>();
        createDummyData();

//        ArrayList<String> alName = new ArrayList<>();
//        alName.add("friend1");
//        alName.add("friend2");
//        alName.add("friend3");
//
//        ArrayList<Boolean> alStatus = new ArrayList<>();
//
//        alStatus.add(false);
//        alStatus.add(true);
//        alStatus.add(true);
//
//        ArrayList<String> alEmail= new ArrayList<>();
//
//        alEmail.add("abc@bmail.com");
//        alEmail.add("def@email.fom");
//        alEmail.add("ghi@hmail.iom");

        CustomAdapter customAdapter = new CustomAdapter(this, friends);

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDummyData() {
        friends.add(new Friend("100"));
        friends.add(new Friend("101"));
        friends.add(new Friend("102"));
        friends.add(new Friend("103"));
        friends.add(new Friend("104"));
        friends.add(new Friend("105"));
        friends.add(new Friend("106"));
        friends.add(new Friend("107"));
        friends.add(new Friend("108"));
        friends.add(new Friend("109"));
        friends.add(new Friend("110"));
        friends.add(new Friend("111"));
        friends.add(new Friend("112"));
    }


    class CustomAdapter extends ArrayAdapter<Friend> {
        Context context;

        ArrayList<Friend> friends;
//        ArrayList<String> names;
//        ArrayList<String> emails;
//        ArrayList<Boolean> awakeStatus;
//        String descriptions[];

        CustomAdapter(Context c, ArrayList<Friend> friends) {
            super(c, R.layout.res_layout_row_awake_status_list, R.id.tv_friendName, friends);
            this.context = c;
            this.friends = friends;
//            this.names = names;
//            this.awakeStatus = awakeStatus;
//            this.emails = emails;
//            this.descriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.res_layout_row_awake_status_list, parent,
                    false);

            TextView tvFriendName = row.findViewById(R.id.tv_friendName);
            tvFriendName.setText(friends.get(position).getUserName());

            TextView tvFriendEmail = row.findViewById(R.id.tv_friendEmail);
            tvFriendEmail.setText(friends.get(position).getEmail());

            ImageView ivAwakeStatus = row.findViewById(R.id.iv_awakeStatus);
            ImageView btnCall = row.findViewById(R.id.btn_call);
            if (friends.get(position).getIsAwake()) {
                ivAwakeStatus.setImageResource(R.drawable.ic_awake_green);
            } else {
                ivAwakeStatus.setImageResource(R.drawable.ic_sleep_red);
            }
//            btnCall.setBackgroundTintList(context.getResources().getColorStateList(R.color
//            .colorAccent));
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "call " + friends.get(position).getUserName(),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:0125389672"));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        checkUserPhonePermission();
//                    }

                    startActivity(intent);


                }
            });
//            System.out.println(titles.get(position));


            return row;
        }
    }

    public boolean checkUserPhonePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        99);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        99);
            }
            return false;
        } else {
            return true;
        }
    }
}
