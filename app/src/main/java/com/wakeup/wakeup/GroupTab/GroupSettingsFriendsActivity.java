package com.wakeup.wakeup.GroupTab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakeup.wakeup.Home;
import com.wakeup.wakeup.MainActivity;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.ObjectClass.Friend;
import com.wakeup.wakeup.ObjectClass.Group;
import com.wakeup.wakeup.ObjectClass.GroupMember;
import com.wakeup.wakeup.R;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GroupSettingsFriendsActivity extends AppCompatActivity {
    private ListView lv;
    private ArrayList<GroupMember> members;
    private DatabaseReference dbGroups;
    String groupKey;
    private ArrayList<GroupMember> allContacts;
    String currentUserPhoneNum;
    ActionBar tb;
    FirebaseHelper firebaseHelper;
    Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_settings_friends);
        members = new ArrayList<>();
        allContacts = new ArrayList<>();
        groupKey = getIntent().getExtras().getString("GroupKey");
        group = getIntent().getExtras().getParcelable("GroupKey");
        dbGroups = FirebaseDatabase.getInstance().getReference("groups");
        tb = getSupportActionBar();
        getContactList();


        tb.setDisplayHomeAsUpEnabled(true);

        lv = findViewById(R.id.lv_GroupFriends);


//        createDummyData();


        firebaseHelper = new FirebaseHelper();

        currentUserPhoneNum = firebaseHelper.getPhoneNum();

        retrieveAllAdmins(groupKey);

//        retrieveAllMembers(groupKey);

//        handleLayoutSelfAdmin(currentUserPhoneNum, groupKey);
//        boolean amIAdmin = true;
//
//        CustomAdapter customAdapter = new CustomAdapter(this, members, amIAdmin);
//
//        lv.setAdapter(customAdapter);
//        Button btnLeaveDeleteGroup = findViewById(R.id.btn_leaveDeleteGroup);
//        if (amIAdmin) {
//            btnLeaveDeleteGroup.setText(R.string.delete_group);
//        }

    }

    public void retrieveAllAdmins(String groupKey) {
        dbGroups.child(groupKey).child("admins").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int n;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String phoneNum = postSnapshot.getKey();
                    n = allContacts.stream().filter(o -> phoneNum.equals(o.getPhoneNum())).collect(Collectors.toList()).size();
                    String name;
                    System.out.println(currentUserPhoneNum);
                    System.out.println(phoneNum);

                    if (currentUserPhoneNum.equals(phoneNum)) {
                        name = "Me";
                    } else if (n > 0) {
                        name = allContacts.stream().filter(o -> phoneNum.equals(o.getPhoneNum())).collect(Collectors.toList()).get(0).getUserName();
                    } else {
                        name = phoneNum;
                    }
                    members.add(new GroupMember(name, true, phoneNum));
                }
                retrieveAllMembers(groupKey);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void retrieveAllMembers(String groupKey) {
        dbGroups.child(groupKey).child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int n;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String phoneNum = postSnapshot.getKey();
                    n = allContacts.stream().filter(o -> phoneNum.equals(o.getPhoneNum())).collect(Collectors.toList()).size();

                    boolean isAdmin =
                            members.stream().filter(o -> phoneNum.equals(o.getPhoneNum()) && o.isAdmin()).collect(Collectors.toList()).size() != 0;
                    if (isAdmin) {
                        continue;
                    }
                    String name;
                    if (currentUserPhoneNum.equals(phoneNum)) {
                        name = "Me";
                    } else if (n > 0) {
                        name = allContacts.stream().filter(o -> phoneNum.equals(o.getPhoneNum())).collect(Collectors.toList()).get(0).getUserName();
                    } else {
                        name = phoneNum;
                    }
                    members.add(new GroupMember(name, false, phoneNum));
                }
                handleLayoutSelfAdmin(currentUserPhoneNum, groupKey);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

                        if (names.contains(name)) {
                            continue;
                        }

                        System.out.println(phoneNo.replaceAll("\\s+", "").replaceAll("-+", ""));
                        System.out.println(name);
//                        System.out.println(phoneNo);
                        names.add(name);
                        allContacts.add(new GroupMember(name, false, phoneNo.replaceAll("\\s+",
                                "").replaceAll("-+", "")));
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }

    public void handleLayoutSelfAdmin(String phoneNum, String groupKey) {
        boolean isAdmin = false;
        dbGroups.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(groupKey).child("admins").child(phoneNum).exists()) {
                    //user exists, do something
                    CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),
                            members, true);

                    lv.setAdapter(customAdapter);
                    Button btnLeaveDeleteGroup = findViewById(R.id.btn_leaveDeleteGroup);
                    btnLeaveDeleteGroup.setText(R.string.delete_group);

                    btnLeaveDeleteGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            firebaseHelper.deleteGroup(groupKey);
                            Toast.makeText(getApplicationContext(), "Group Deleted!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                    lv.setAdapter(customAdapter);
                } else {
                    CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),
                            members, false);
                    lv.setAdapter(customAdapter);
                    Button btnLeaveDeleteGroup = findViewById(R.id.btn_leaveDeleteGroup);
                    btnLeaveDeleteGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            firebaseHelper.removeUserFromGroup(currentUserPhoneNum, groupKey);
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                }
                tb.setTitle("Friends List (" + (members.size()) + ")");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


//    private void createDummyData() {
//        members.add(new GroupMember("me", true, "0123456987"));
//        members.add(new GroupMember("A", false, "0123456987"));
//        members.add(new GroupMember("B", false, "0123456987"));
//        members.add(new GroupMember("C", false, "0123456987"));
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class CustomAdapter extends ArrayAdapter<GroupMember> {
        Context context;
        ArrayList<GroupMember> members;
        boolean amIAdmin;

        CustomAdapter(Context c, ArrayList<GroupMember> members, boolean amIAdmin) {
            super(c, R.layout.res_layout_row_awake_status_list, R.id.tv_friendName, members);
            this.context = c;
            this.amIAdmin = amIAdmin;
            this.members = members;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.res_layout_row_group_member_list, parent,
                    false);

            TextView tvFriendName = row.findViewById(R.id.tv_friendName);
            TextView tvFriendEmail = row.findViewById(R.id.tv_friendPhoneNumber);
            TextView tvAdmin = row.findViewById(R.id.tv_admin);
            ImageView ivRemove = row.findViewById(R.id.iv_removeMember);
            ImageView ivBtnCall = row.findViewById(R.id.btn_call);

            String name = members.get(position).getUserName();
            String phoneNum = members.get(position).getPhoneNum();
            boolean isAdmin = members.get(position).isAdmin();


            tvFriendName.setText(name);
            tvFriendEmail.setText(phoneNum);


            if (name.equals("Me")) {
                ivBtnCall.setVisibility(View.GONE);
                ivRemove.setVisibility(View.GONE);
                if (isAdmin) {
                    tvAdmin.setText(R.string.admin);
                    tvAdmin.setBackgroundResource(R.drawable.design_text_view_border_green);
                }
            } else {
                if (isAdmin) {
                    tvAdmin.setText(R.string.admin);
                    ivRemove.setVisibility(View.GONE);
                    tvAdmin.setBackgroundResource(R.drawable.design_text_view_border_green);

                } else {

                    if (!amIAdmin) {
                        ivRemove.setVisibility(View.GONE);
                    } else {
                        ivRemove.setImageResource(R.drawable.ic_delete_red_24dp);
                    }
                }
            }


//            if (amIAdmin) {
//                btnLeaveDeleteGtoup.setText(R.string.delete_group);
//            }
//            else {
//                tvAdmin.setText(" ");
//                ivRemove.setImageResource(0);
//            }

            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "remove " + members.get(position).getUserName(),
                            Toast.LENGTH_SHORT).show();
                    firebaseHelper.removeUserFromGroup(phoneNum, groupKey);
                    members.remove(members.get(position));
                    notifyDataSetChanged();
                }
            });
            ivBtnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "call " + members.get(position).getUserName(),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phoneNum));
                    startActivity(intent);
                }
            });

            return row;
        }
    }
}
