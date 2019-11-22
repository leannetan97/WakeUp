package com.wakeup.wakeup.ui.main;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.ObjectClass.Group;
import com.wakeup.wakeup.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {
    private RecyclerView rvGroup;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter groupAdapter;
    private List<Group> groups;

    public GroupFragment() {
        // Required empty public constructor
        groups = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        createDummyData();

        rvGroup = view.findViewById(R.id.rv_group);
        layoutManager = new LinearLayoutManager(getContext());
        rvGroup.setLayoutManager(layoutManager);
        groupAdapter = new GroupFragmentAdapter(groups);
        rvGroup.setAdapter(groupAdapter);
//        GroupFragment.GroupListViewAdapter groupCardListAdapter =
//                new GroupFragment.GroupListViewAdapter(getContext(), groupName);
//        rvGroup.setAdapter(groupCardListAdapter);
        return view;
    }

    private void createDummyData() {
        Group temp = new Group("Group A");
        groups.add(temp);
        createDummyAlarmData(temp);
        temp = new Group("Group B");
        groups.add(temp);
        createDummyAlarmData(temp);
        temp = new Group("Group C");
        groups.add(temp);
        createDummyAlarmData(temp);
        temp = new Group("Group D");
        groups.add(temp);
        createDummyAlarmData(temp);
        temp = new Group("Group E");
        groups.add(temp);
        temp = new Group("Group F");
        groups.add(temp);
        createDummyAlarmData(temp);
    }

    private void createDummyAlarmData(Group group){
        group.addAlarm(new Alarm("02:00","Alarm Name 1", true, true,2));
        group.addAlarm(new Alarm("07:00","Alarm Name 2", false, true,2));
        group.addAlarm(new Alarm("13:00","Alarm Name 3", true, true,2));
        group.addAlarm(new Alarm("22:00","Alarm Name 4", false, true,2));
        group.addAlarm(new Alarm("23:00","Alarm Name 5", true, true,2));
    }

//    class GroupListViewAdapter extends ArrayAdapter<String> {
//        Context context;
//        private ArrayList<String> groupName;
//
//
//        GroupListViewAdapter(Context c, ArrayList<String> groupName) {
//            super(c, R.layout.res_group_card_view, R.id.tv_group_name, groupName);
//            this.context = c;
//            this.groupName = groupName;
//        }
//
//        @Override
//        public View getView(int position, View convertView,
//                            ViewGroup parent) {
//            LayoutInflater inflater =
//                    (LayoutInflater) getActivity().getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
//            View card = inflater.inflate(R.layout.res_group_card_view, parent, false);
//            ((TextView) card.findViewById(R.id.tv_group_name)).setText(groupName.get(position));
//            return card;
//        }
//    }
}

