package com.wakeup.wakeup.GroupTab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wakeup.wakeup.AwakeStatusListActivity;
import com.wakeup.wakeup.ListFriendsActivity;
import com.wakeup.wakeup.ObjectClass.Friend;
import com.wakeup.wakeup.R;
import com.wakeup.wakeup.ui.main.NewGroupFriendsListAdapter;

import java.util.ArrayList;


public class NewGroupActivity extends AppCompatActivity {
    ArrayList<Friend> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        friends = new ArrayList<>();
        createDummyData();

        ActionBar ab = getSupportActionBar();
//        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.new_group_text);

        Button btnAddFromList = (Button) findViewById(R.id.btn_groupInvite);
        btnAddFromList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO: Implemenet invite add to list
//                Intent I = new Intent(NewGroupActivity.this, AwakeStatusListActivity.class);
//                startActivity(I);
            }
        });

        RelativeLayout friendsList = findViewById(R.id.relative_layout_friends_list);
        friendsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListFriendsActivity.class);
                startActivityForResult(intent, 0);
            }
        });


//        Toolbar tbNewGroup = findViewById(R.id.tb_NewGroup);
//        setSupportActionBar(tbNewGroup);
//        ActionBar ab = getSupportActionBar();
//
//        // Enable the Up button
////        assert ab != null;
//        ab.setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.rv_new_group_friends_list);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("Name");
                Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            }
        }
    }

//    @Override
//    public boolean onNavigateUp() {
//        finish();
//        return true;
//
//    }
}
