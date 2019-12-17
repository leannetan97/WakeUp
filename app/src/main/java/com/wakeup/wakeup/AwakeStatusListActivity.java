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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import java.util.Collections;
import java.util.stream.Collectors;

public class AwakeStatusListActivity extends AppCompatActivity {

    private ArrayList<Friend> friendsAwake, friendsSleep;
    private ListView lvAwake, lvSleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awake_status_list);
        ActionBar tb = getSupportActionBar();
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Awake Status List");

        friendsAwake = new ArrayList<>();
        createDummyData();

        int nFriendsNotAwake;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            nFriendsNotAwake =
                    friendsAwake.stream().filter(o -> !o.getIsAwake()).collect(Collectors.toList()).size();
        } else {
            nFriendsNotAwake = 0;
            for (Friend f : friendsAwake) {
                if (!f.getIsAwake()) {
                    nFriendsNotAwake += 1;
                }
            }
        }

        if(nFriendsNotAwake > 0){
            tb.setTitle("(" + nFriendsNotAwake + ") is/are still sleeping");
        }else {
            tb.setTitle("All members are awake!");
        }



        lvAwake = (ListView) findViewById(R.id.lv_awakeStatusList_awake);
//        lvSleep = (ListView) findViewById(R.id.lv_awakeStatusList_sleep);


//        friendsSleep = new ArrayList<>();


        CustomAdapter customAdapterAwake = new CustomAdapter(this, friendsAwake);
//        CustomAdapter customAdapterSleep = new CustomAdapter(this, friendsSleep);

        lvAwake.setAdapter(customAdapterAwake);
//        lvSleep.setAdapter(customAdapterSleep);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.awake_status_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.btn_refresh_awake_status_list_menu) {
            // TODO: implement refresh button
            Toast.makeText(this, "Refreshing list...", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDummyData() {
        int n = 100;
        for (int i = 0; i < 5; i++) {
            friendsAwake.add(new Friend(Integer.toString(n), false));
            n += 1;
        }
        n = 200;
        for (int i = 0; i < 7; i++) {
            friendsAwake.add(new Friend(Integer.toString(n), true));
            n += 1;
        }
        Collections.sort(friendsAwake);
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

            TextView tvFriendPhone = row.findViewById(R.id.tv_friendPhoneNumber);
            System.out.println(friends.get(position).getPhoneNum());
            tvFriendPhone.setText(friends.get(position).getPhoneNum());

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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        99);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        99);
            }
            return false;
        } else {
            return true;
        }
    }
}
