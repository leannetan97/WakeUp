package com.wakeup.wakeup.ui.main;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wakeup.wakeup.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {
    private ListView lvGroup;
    private ArrayList<String> groupName = new ArrayList<>();

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        lvGroup = view.findViewById(R.id.lv_group);
        createDummyData();
        GroupFragment.GroupListViewAdapter groupCardListAdapter =
                new GroupFragment.GroupListViewAdapter(getContext(), groupName);
        lvGroup.setAdapter(groupCardListAdapter);
        return view;
    }

    private void createDummyData() {
        groupName.add("Group A");
        groupName.add("Group B");
        groupName.add("Group C");
        groupName.add("Group D");
        groupName.add("Group E");
        groupName.add("Group F");
        groupName.add("Group G");
    }

    class GroupListViewAdapter extends ArrayAdapter<String> {
        Context context;
        private ArrayList<String> groupName;


        GroupListViewAdapter(Context c, ArrayList<String> groupName) {
            super(c, R.layout.res_group_card_view, R.id.tv_group_name, groupName);
            this.context = c;
            this.groupName = groupName;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            LayoutInflater inflater =
                    (LayoutInflater) getActivity().getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View card = inflater.inflate(R.layout.res_group_card_view, parent, false);
            ((TextView) card.findViewById(R.id.tv_group_name)).setText(groupName.get(position));
            return card;
        }
    }
}

