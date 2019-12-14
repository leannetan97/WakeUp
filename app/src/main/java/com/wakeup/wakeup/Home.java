package com.wakeup.wakeup;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wakeup.wakeup.GroupTab.NewGroupActivity;
import com.wakeup.wakeup.HistoryTab.LeaderboardActivity;
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.ObjectClass.User;
import com.wakeup.wakeup.UserLogin.ChangePassword;
import com.wakeup.wakeup.ui.main.AlarmFragment;
import com.wakeup.wakeup.ui.main.GroupFragment;
import com.wakeup.wakeup.ui.main.HistoryFragment;
import com.wakeup.wakeup.ui.main.HomeFragment;
import com.wakeup.wakeup.ui.main.ViewPagerAdapter;

public class Home extends AppCompatActivity implements DialogWithTitle.DialogListener {
    // temp
    private FirebaseHelper firebaseHelper;
    //////////////////

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabAddAlarm, fabAddGroup, fabLeaderboard;

    private int[] tabIcons = {
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_access_alarm_white_24dp,
            R.drawable.ic_group_white_24dp,
            R.drawable.ic_equalizer_white_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // set action bar
        getSupportActionBar();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        // temp, for testing
        firebaseHelper = new FirebaseHelper();

        Button button = (Button)findViewById(R.id.button_temp);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firebaseHelper.addHistory(6);
            }
        });
        ///////////

        fabAddAlarm = (FloatingActionButton) findViewById(R.id.btn_floating_add_alarm);
        fabAddGroup = (FloatingActionButton) findViewById(R.id.btn_floating_add_group);
        fabLeaderboard = (FloatingActionButton) findViewById(R.id.btn_floating_trophy);


        fabAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCreateAlarm(v);
            }
        });

        fabAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCreateGroup(v);
            }
        });

        fabLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLeaderBoard(v);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                animateFab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                animateFab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //TODO: Implement navigation
            case R.id.item_change_name:
                openDialog();
                return true;
            case R.id.item_change_password:
                navigateToChangePassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void navigateToChangePassword() {
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }

    // Change ProfileName
    private void openDialog() {
        DialogWithTitle changeNameDialog = new DialogWithTitle();

        Bundle args = new Bundle();
        args.putString("DialogTitle", "Change Profile Name");
        args.putString("Hint","Change Profile Name");
        args.putString("ValidButton", "SAVE");
        args.putString("InvalidButton", "DISCARD");
        changeNameDialog.setArguments(args);
        changeNameDialog.show(getSupportFragmentManager(), "change profile name dialog");
    }

    @Override
    public void applyTexts(String profileName) {
        if(profileName.length() == 0){
            Toast.makeText(this,"Name cannot be empty. Please try again.",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Profile name changed to " + profileName, Toast.LENGTH_SHORT).show();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "HOME");
        adapter.addFragment(new AlarmFragment(), "ALARM");
        adapter.addFragment(new GroupFragment(), "GROUP");
        adapter.addFragment(new HistoryFragment(), "HISTORY");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void animateFab(int i) {
        switch (i) {
            case 1:
                fabAddAlarm.show();
                fabAddGroup.hide();
                fabLeaderboard.hide();
                break;
            case 2:
                fabAddAlarm.hide();
                fabAddGroup.show();
                fabLeaderboard.hide();
                break;
            case 3:
                fabAddAlarm.hide();
                fabAddGroup.hide();
                fabLeaderboard.show();
                break;
            default:
                fabAddAlarm.hide();
                fabAddGroup.hide();
                fabLeaderboard.hide();
                break;
        }
    }
    // New Alarm
    private void navigateToCreateAlarm(View view) {
        Intent alarmView = new Intent(Home.this, CreateDeleteAlarm.class);
        alarmView.putExtra("ViewTitle", "New Personal Alarm");
        alarmView.putExtra("ButtonName", "Create Alarm");
        startActivity(alarmView);
    }

    // NewGroup
    private void navigateToCreateGroup(View view) {
        Intent createGroupView = new Intent(Home.this, NewGroupActivity.class);
        startActivity(createGroupView);
    }

    // Leaderboard
    private void navigateToLeaderBoard(View view) {
        Intent leaderBoard = new Intent(Home.this, LeaderboardActivity.class);
        startActivity(leaderBoard);
    }
}