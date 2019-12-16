package com.wakeup.wakeup.GroupTab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakeup.wakeup.AwakeStatusListActivity;
import com.wakeup.wakeup.ListFriendsActivity;
import com.wakeup.wakeup.ObjectClass.Friend;
import com.wakeup.wakeup.R;
import com.wakeup.wakeup.ui.main.NewGroupFriendsListAdapter;

import java.util.ArrayList;


public class NewGroupActivity extends AppCompatActivity {
    ArrayList<Friend> friends;
    RecyclerView recyclerView;
    private DatabaseReference dbUsers;
    private boolean exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        dbUsers = FirebaseDatabase.getInstance().getReference("users");
        // Friends Selected
        friends = new ArrayList<>();

        ActionBar ab = getSupportActionBar();
//        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.new_group_text);

        Button btnAddFromList = (Button) findViewById(R.id.btn_groupInvite);
        btnAddFromList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO: Implemenet invite add to list
                TextView tvPhoneNumber = findViewById(R.id.et_enterPhoneNumber);
                String phoneNumber = tvPhoneNumber.getText().toString();
                // TODO: Check if phone number is in database
                if (checkExistInDatabase(phoneNumber)) {
                    friends.add(new Friend(phoneNumber, phoneNumber));
                } else {
                    Toast.makeText(getApplicationContext(), "Phone Number is not registered yet!"
                            , Toast.LENGTH_SHORT).show();
                }
                tvPhoneNumber.setText("");
            }
        });

        RelativeLayout friendsList = findViewById(R.id.relative_layout_friends_list);
        friendsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListFriendsActivity.class);
                startActivityForResult(intent, 0);
                Toast.makeText(getApplicationContext(), "Loading Contacts...",
                        Toast.LENGTH_SHORT).show();
            }
        });


//        Toolbar tbNewGroup = findViewById(R.id.tb_NewGroup);
//        setSupportActionBar(tbNewGroup);
//        ActionBar ab = getSupportActionBar();
//
//        // Enable the Up button
////        assert ab != null;
//        ab.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_new_group_friends_list);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NewGroupFriendsListAdapter adapter = new NewGroupFriendsListAdapter(this, friends);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_group_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_newGroupOK:
                Toast.makeText(this, "OK!", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
//                String name = data.getStringExtra("Name");
                ArrayList<Friend> friendsSelected = data.getParcelableArrayListExtra("friends");
//                System.out.println(friendsSelected.get(0));
                friends.addAll(friendsSelected);
                NewGroupFriendsListAdapter adapter = new NewGroupFriendsListAdapter(this, friends);
                recyclerView.setAdapter(adapter);
//                Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkExistInDatabase(Friend friend) {
        boolean isAdmin = false;
        dbUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(friend.getPhoneNumber()).exists()) {
                    //user exists, do something
                    friends.add(friend);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean checkExistInDatabase(String phoneNumber) {
        dbUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phoneNumber).exists()) {
                    //user exists, do something
                    exist = true;
                } else {
                    exist = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return exist;
    }


}
