package com.wakeup.wakeup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakeup.wakeup.ObjectClass.Friend;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ListFriendsActivity extends AppCompatActivity {
    ArrayList<Friend> friends = new ArrayList<>();
    ArrayList<Friend> allContacts = new ArrayList<>();
    private DatabaseReference dbUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friends);
//        createDummyData();
        checkUserPhonePermission();
        dbUsers = FirebaseDatabase.getInstance().getReference("users");
        getContactList();
        for (Friend f : allContacts) {
            if (f.getPhoneNumber().contains(".") || f.getPhoneNumber().contains("#") || f.getPhoneNumber().contains("$") || f.getPhoneNumber().contains("[") || f.getPhoneNumber().contains("]")) {
                continue;
            }
            checkExistInDatabase(f);
        }

        ActionBar ab = getSupportActionBar();
        String title = String.format("Friends List (%d)", friends.size());
        ab.setTitle(title);

//        assert ab != null;
//        ab.setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.lv_list_friends);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout
//        .fragment_friend_lists_checkbox, R.id.tv_friend_name_list_view, friends);
        ArrayAdapter<Friend> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, friends);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
                Friend friend = (Friend) listView.getItemAtPosition(position);
                friend.setChecked(currentCheck);
            }
        });

    }

    //    private void createDummyData() {
//        friends.add(new Friend("100"));
//        friends.add(new Friend("101"));
//        friends.add(new Friend("102"));
//        friends.add(new Friend("103"));
//        friends.add(new Friend("104"));
//        friends.add(new Friend("105"));
//        friends.add(new Friend("106"));
//        friends.add(new Friend("107"));
//        friends.add(new Friend("108"));
//        friends.add(new Friend("109"));
//        friends.add(new Friend("110"));
//        friends.add(new Friend("111"));
//        friends.add(new Friend("112"));
//    }

    private void filterContacts() {

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

    private void getContactList() {
        ArrayList<String> names = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);


        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        System.out.println(name);
                        if (names.contains(name)) {
                            continue;
                        }
                        System.out.println(phoneNo);
                        names.add(name);
                        allContacts.add(new Friend(name, phoneNo));
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.friend_list_check_box_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_friend_list_text_OK) {
            Intent intent = new Intent();
//            intent.putExtra("Name", "OK");
            intent.putParcelableArrayListExtra("friends", getSelectedFriends());
            setResult(RESULT_OK, intent);
//            System.out.println(getSelectedFriends().get(0));
//            Toast.makeText(this, "OK!", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Friend> getSelectedFriends() {
        ArrayList<Friend> selectedFriends = new ArrayList<>();
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).getChecked()) {
                selectedFriends.add(friends.get(i));
            }
        }
        return selectedFriends;
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
}

