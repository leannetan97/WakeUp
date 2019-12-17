package com.wakeup.wakeup.HistoryTab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.ObjectClass.Game;
import com.wakeup.wakeup.R;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    // firebase
    private DatabaseReference dbScores;
    private List<Game> leaders;

    // adapter
    private ListView listView;
    private LeaderboardAdapter leaderboardAdapter;

    public LeaderboardActivity() {
        // Required empty public constructor
        leaders = new ArrayList<>();

        // firebase
        dbScores = new FirebaseHelper().getDbScores();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // List
        listView = (ListView)findViewById(R.id.leaderboardList);
        leaderboardAdapter = new LeaderboardAdapter(this, R.layout.activity_leaderboard_view, leaders);
        listView.setAdapter(leaderboardAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        dbScores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // clear previous list
                leaders.clear();

                // iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Game game = postSnapshot.getValue(Game.class);

                    leaders.add(game);
                }

                // create adapter
                leaderboardAdapter = new LeaderboardAdapter(getApplicationContext(), R.layout.activity_leaderboard_view, leaders);
                listView.setAdapter(leaderboardAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
