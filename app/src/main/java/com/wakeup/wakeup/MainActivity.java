package com.wakeup.wakeup;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wakeup.wakeup.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity implements ChangeProfileNameDialog.dialogListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // History
        Button btnHistory = (Button) findViewById(R.id.btn_history);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(I);
            }
        });

        // NewGroup
        Button btnNewGroup = (Button) findViewById(R.id.btn_newGroup);
        btnNewGroup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                openDialog();
                Intent I = new Intent(MainActivity.this, SingleGroupActivity.class);
                startActivity(I);
            }
        });

        // LeaderBoardActivity
        Button btnLeaderboard = (Button)findViewById(R.id.btn_leaderboard);
        btnLeaderboard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(I);
            }
        });

    }

    void openDialog() {
        ChangeProfileNameDialog dialog = new ChangeProfileNameDialog();
        dialog.show(getSupportFragmentManager(), "change profile name dialog");
    }

    @Override
    public void applyTexts(String profileName) {
        Toast.makeText(this,"Profile name changed to " + profileName, Toast.LENGTH_SHORT).show();
    }
}