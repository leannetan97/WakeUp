package com.wakeup.wakeup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ListFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friends);

        ActionBar ab = getSupportActionBar();
//        assert ab != null;
//        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Friends List (4)");
    }
}
