package com.wakeup.wakeup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter<LeaderboardModel> {
    private int resourceLayout;
    private Context mContext;
    private ArrayList<LeaderboardModel> leaderboardModelArrayList;

    public LeaderboardAdapter(@NonNull Context context, int resource, @NonNull List<LeaderboardModel> objects) {
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

        LeaderboardModel p = getItem(position);

        if (p != null) {
            TextView tv1 = (TextView) v.findViewById(R.id.tv_leaderboardViewList1);
            TextView tv2 = (TextView) v.findViewById(R.id.tv_leaderboardViewList2);

            if (tv1 != null) {
                tv1.setText(p.getName());
            }
            if (tv2 != null) {
                tv2.setText(Integer.toString(p.getScore()));
            }
        }

        return v;
    }
}
