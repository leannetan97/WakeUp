package com.wakeup.wakeup.HistoryTab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.wakeup.wakeup.ObjectClass.LeaderboardModel;
import com.wakeup.wakeup.R;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    ArrayList leaderEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // List
        ListView listView = (ListView)findViewById(R.id.leaderboardList);

        getLeadList();

        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(this, R.layout.activity_leaderboard_view, leaderEntries);

        listView.setAdapter(leaderboardAdapter);
    }

    private void getLeadList() {
        leaderEntries = new ArrayList<LeaderboardModel>();
        leaderEntries.add(new LeaderboardModel("Leanne", 7));
        leaderEntries.add(new LeaderboardModel("Lim", 6));
        leaderEntries.add(new LeaderboardModel("Leo", 4));
        leaderEntries.add(new LeaderboardModel("Lin", 4));

    }
}
