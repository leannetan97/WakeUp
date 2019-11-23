package com.wakeup.wakeup.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wakeup.wakeup.ObjectClass.Friend;
import com.wakeup.wakeup.R;

import java.util.ArrayList;

public class NewGroupFriendsListAdapter extends RecyclerView.Adapter<NewGroupFriendsListAdapter.NewGroupFriendsListViewHolder> {

    private ArrayList<Friend> friends;
    private Context context;

    public NewGroupFriendsListAdapter(Context context, ArrayList<Friend> friends) {
        this.friends = friends;
        this.context = context;
    }

    @NonNull
    @Override
    public NewGroupFriendsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                            int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_new_group_friends_list_item, parent, false);
        NewGroupFriendsListViewHolder viewHolder = new NewGroupFriendsListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewGroupFriendsListViewHolder holder, int position) {
        holder.itemView.setTag(friends.get(position));
        Friend friend = friends.get(position);
        holder.tvFriendName.setText(friend.getUserName());

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class NewGroupFriendsListViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFriendName;
        public ImageView ivRemove;

        public NewGroupFriendsListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFriendName = itemView.findViewById(R.id.tv_new_group_friends_list_name);
            ivRemove = itemView.findViewById(R.id.iv_new_group_friends_list_remove);

            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Removed " + friends.get(getAdapterPosition()).getUserName() , Toast.LENGTH_SHORT).show();
                    friends.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
         }
    }
}
