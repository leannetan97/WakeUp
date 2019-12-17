package com.wakeup.wakeup.HistoryTab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wakeup.wakeup.ObjectClass.Game;
import com.wakeup.wakeup.R;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter<Game> {
    private int resourceLayout;
    private Context mContext;
    private ArrayList<Game> leaderboardModelArrayList;

    public LeaderboardAdapter(@NonNull Context context, int resource, @NonNull List<Game> objects) {
        super(context, resource, objects);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Game p = getItem(position);

        if (p != null) {
            TextView tv1 = (TextView) v.findViewById(R.id.tv_leaderboardViewList1);
            TextView tv2 = (TextView) v.findViewById(R.id.tv_leaderboardViewList2);

            if (tv1 != null) {
                tv1.setText(p.getUser());
            }
            if (tv2 != null) {
                tv2.setText(Integer.toString(p.getScore()));
            }
        }

        return v;
    }
}
