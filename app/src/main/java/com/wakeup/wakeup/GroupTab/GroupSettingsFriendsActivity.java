package com.wakeup.wakeup.GroupTab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wakeup.wakeup.ObjectClass.GroupMember;
import com.wakeup.wakeup.R;

import java.util.ArrayList;

public class GroupSettingsFriendsActivity extends AppCompatActivity {
    private ListView lv;
    private ArrayList<GroupMember> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_settings_friends);

        ActionBar tb = getSupportActionBar();
        tb.setDisplayHomeAsUpEnabled(true);

        lv = (ListView) findViewById(R.id.lv_GroupFriends);

        members = new ArrayList<>();

        createDummyData();


        tb.setTitle("Friends List (" + (members.size() - 1) + ")");
        boolean amIAdmin = true;

        CustomAdapter customAdapter = new CustomAdapter(this, members, amIAdmin);

        lv.setAdapter(customAdapter);
        Button btnLeaveDeleteGroup = findViewById(R.id.btn_leaveDeleteGroup);
        if (amIAdmin) {
            btnLeaveDeleteGroup.setText(R.string.delete_group);
        }

    }

    private void createDummyData(){
        members.add(new GroupMember("me", true, "0123456987"));
        members.add(new GroupMember("A", false, "0123456987"));
        members.add(new GroupMember("B", false, "0123456987"));
        members.add(new GroupMember("C", false, "0123456987"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class CustomAdapter extends ArrayAdapter<GroupMember> {
        Context context;
        ArrayList<GroupMember> members;
        boolean amIAdmin;

        CustomAdapter(Context c, ArrayList<GroupMember> members, boolean amIAdmin) {
            super(c, R.layout.res_layout_row_awake_status_list, R.id.tv_friendName, members);
            this.context = c;
            this.amIAdmin = amIAdmin;
            this.members = members;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.res_layout_row_group_member_list, parent,
                    false);

            TextView tvFriendName = row.findViewById(R.id.tv_friendName);
            TextView tvFriendEmail = row.findViewById(R.id.tv_friendEmail);
            TextView tvAdmin = row.findViewById(R.id.tv_admin);
            ImageView ivRemove = row.findViewById(R.id.iv_removeMember);
            ImageView ivBtnCall = row.findViewById(R.id.btn_call);

            String name = members.get(position).getUserName();
            String email = members.get(position).getPhoneNum();
            boolean isAdmin = members.get(position).isAdmin();


            tvFriendName.setText(name);
            tvFriendEmail.setText(email);



            if (name.equals("me")) {
                ivBtnCall.setVisibility(View.GONE);
                ivRemove.setVisibility(View.GONE);
                if (isAdmin) {
                    tvAdmin.setText(R.string.admin);
                    tvAdmin.setBackgroundResource(R.drawable.design_text_view_border_green);
                }
            } else {
                if (isAdmin) {
                    tvAdmin.setText(R.string.admin);
                    ivRemove.setVisibility(View.GONE);
                    tvAdmin.setBackgroundResource(R.drawable.design_text_view_border_green);

                } else {

                    if (!amIAdmin) {
                        ivRemove.setVisibility(View.GONE);
                    } else {
                        ivRemove.setImageResource(R.drawable.ic_delete_red_24dp);
                    }
                }
            }


//            if (amIAdmin) {
//                btnLeaveDeleteGtoup.setText(R.string.delete_group);
//            }
//            else {
//                tvAdmin.setText(" ");
//                ivRemove.setImageResource(0);
//            }

            ivBtnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "call " + members.get(position).getUserName(), Toast.LENGTH_SHORT).show();
                }
            });

            return row;
        }
    }
}
