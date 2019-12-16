package com.wakeup.wakeup.HistoryTab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.wakeup.wakeup.ObjectClass.History;
import com.wakeup.wakeup.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HistoryAdapter extends ArrayAdapter<History> {

    private int resourceLayout;
    private Context mContext;
    private ArrayList<History> historyArrayList;

    private SimpleDateFormat dateFormatter =new SimpleDateFormat("EEEE   MMM dd   HH:mm");

    public HistoryAdapter(@NonNull Context context, int resource, @NonNull List<History> objects) {
        super(context, resource, objects);
        this.resourceLayout = resource;
        this.mContext = context;
        this.historyArrayList = (ArrayList<History>) objects;
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

        History history = getItem(position);

        if (history != null) {
            TextView tv1 = (TextView) v.findViewById(R.id.tv_historyViewList1);
            TextView tv2 = (TextView) v.findViewById(R.id.tv_historyViewList2);

            if (tv1 != null) {
                Long date = history.getDate();
                String dateStr = "";
                try{
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM   HH:mm");
                    dateStr = dateFormat.format(date);
                } catch(Exception e) {
                }
                tv1.setText(dateStr);
            }
            if (tv2 != null) {
                tv2.setText(Integer.toString(history.getDelay()));
            }
        }

        return v;
    }
}
