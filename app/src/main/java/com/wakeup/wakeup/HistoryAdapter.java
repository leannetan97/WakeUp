package com.wakeup.wakeup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wakeup.wakeup.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryModel> {

    private int resourceLayout;
    private Context mContext;
    private ArrayList<HistoryModel> historyModelArrayList;
    private SimpleDateFormat dateFormatter =new SimpleDateFormat("EEEE, MMM dd hh:mm:ss a");

    public HistoryAdapter(@NonNull Context context, int resource, @NonNull List<HistoryModel> objects) {
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

        HistoryModel p = getItem(position);

        if (p != null) {
            TextView tv1 = (TextView) v.findViewById(R.id.tv_historyViewList1);
            TextView tv2 = (TextView) v.findViewById(R.id.tv_historyViewList2);

            if (tv1 != null) {
                Date date = p.getDate();
                String dateStr = dateFormatter.format(date);
                tv1.setText(dateStr);
            }
            if (tv2 != null) {
                tv2.setText(Integer.toString(p.getDelay()));
            }
        }

        return v;
    }
}
