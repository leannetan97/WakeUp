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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.ObjectClass.Friend;
import com.wakeup.wakeup.ObjectClass.GroupMember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class AwakeStatusListActivity extends AppCompatActivity {

    private ArrayList<Friend> friendsAwake, friendsSleep, friends;
    private ListView lvAwake, lvSleep;
    private ArrayList<Friend> allContacts;
    private DatabaseReference dbGroups;
    FirebaseHelper firebaseHelper;
    String currentUserPhoneNum;
    ActionBar tb = getSupportActionBar();
    String groupKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awake_status_list);

        firebaseHelper = new FirebaseHelper();

        currentUserPhoneNum = firebaseHelper.getPhoneNum();

        allContacts = getIntent().getExtras().getParcelableArrayList("AllContacts");
        groupKey = getIntent().getExtras().getString("GroupKey");

        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Awake Status List");

//        createDummyData();


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

        CustomAdapter(Context c, ArrayList<Friend> friends) {
            super(c, R.layout.res_layout_row_awake_status_list, R.id.tv_friendName, friends);
            this.context = c;
            this.friends = friends;
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
                    intent.setData(Uri.parse("tel:" + friends.get(position).getPhoneNum()));
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

    @Override
    protected void onStart() {
        super.onStart();
        friends = new ArrayList<>();
        dbGroups.child(groupKey).child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int n;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String phoneNum = postSnapshot.getKey();
                    boolean isAwake = (boolean) postSnapshot.getValue();

                    n = allContacts.stream().filter(o -> phoneNum.equals(o.getPhoneNum())).collect(Collectors.toList()).size();

                    String name;
                    if (currentUserPhoneNum.equals(phoneNum)) {
                        continue;
                    } else if (n > 0) {
                        name = allContacts.stream().filter(o -> phoneNum.equals(o.getPhoneNum())).collect(Collectors.toList()).get(0).getUserName();
                    } else {
                        name = phoneNum;
                    }
                    friends.add(new Friend(name, isAwake, phoneNum));
                }
                Collections.sort(friends);

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
                CustomAdapter customAdapterAwake = new CustomAdapter(getApplicationContext(), friends);
                lvAwake.setAdapter(customAdapterAwake);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void retrieveAllMembers(String groupKey) {

    }
}
