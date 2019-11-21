package com.wakeup.wakeup.ui.main;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wakeup.wakeup.GroupTab.SingleGroupActivity;
import com.wakeup.wakeup.ObjectClass.Group;
import com.wakeup.wakeup.R;

import java.util.ArrayList;
import java.util.List;

public class GroupFragmentAdapter extends RecyclerView.Adapter<GroupFragmentAdapter.GroupFragmentViewHolder>{
    private List<Group> groups;

    public GroupFragmentAdapter(List<Group> groups){
        this.groups = groups;
    }

    @NonNull
    @Override
    public GroupFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.res_group_card_view, parent,false);
        return new GroupFragmentViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupFragmentViewHolder holder, int position) {
        Group groupItem = groups.get(position);
        holder.tvGroupName.setText(groupItem.getGroupName());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSingleGroupActivity(view,groupItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    private void navigateToSingleGroupActivity(View view, Group groupItem){
        Intent toSingleGroupActivity = new Intent(view.getContext(), SingleGroupActivity.class);
        toSingleGroupActivity.putExtra("GroupName", groupItem.getGroupName());
        toSingleGroupActivity.putParcelableArrayListExtra("GroupAlarmsList", (ArrayList<?
                extends Parcelable>) groupItem.getAlarmsInGroup());
        view.getContext().startActivity(toSingleGroupActivity);
    }

    public class GroupFragmentViewHolder extends RecyclerView.ViewHolder{
        private TextView tvGroupName;
        private RelativeLayout parentLayout;
        public GroupFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupName = (TextView) itemView.findViewById(R.id.tv_group_name);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.view_group_card);
        }
    }
}
