package com.wakeup.wakeup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupSettingsFriendsActivity extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_settings_friends);

        ActionBar tb = getSupportActionBar();
        tb.setDisplayHomeAsUpEnabled(true);

        lv = (ListView) findViewById(R.id.lv_GroupFriends);

        ArrayList<String> alName = new ArrayList<>();
        alName.add("me");
        alName.add("friend1");
        alName.add("friend2");
        alName.add("friend3");

        ArrayList<Boolean> alAdmin = new ArrayList<>();
        alAdmin.add(false);
        alAdmin.add(true);
        alAdmin.add(false);
        alAdmin.add(false);

        ArrayList<String> alEmail = new ArrayList<>();
        alEmail.add("me@bmail.com");
        alEmail.add("abc@bmail.com");
        alEmail.add("def@email.fom");
        alEmail.add("ghi@hmail.iom");

        tb.setTitle("Friends List (" + (alName.size() - 1) + ")");
        boolean amIAdmin = false;

        CustomAdapter customAdapter = new CustomAdapter(this, alName, alAdmin, alEmail, amIAdmin);

        lv.setAdapter(customAdapter);
        Button btnLeaveDeleteGroup = findViewById(R.id.btn_leaveDeleteGroup);
        if (amIAdmin) {
            btnLeaveDeleteGroup.setText(R.string.delete_group);
        }

    }

    class CustomAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> names;
        ArrayList<String> emails;
        ArrayList<Boolean> isAdmin;
        boolean amIAdmin;
//        String descriptions[];

        CustomAdapter(Context c, ArrayList<String> names, ArrayList<Boolean> isAdmin,
                      ArrayList<String> emails, boolean amIadmin) {
            super(c, R.layout.layout_row_awake_status_list, R.id.tv_friendName, names);
            this.context = c;
            this.names = names;
            this.isAdmin = isAdmin;
            this.emails = emails;
            this.amIAdmin = amIadmin;
//            this.descriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.layout_row_group_member_list, parent, false);

            TextView tvFriendName = row.findViewById(R.id.tv_friendName);
            TextView tvFriendEmail = row.findViewById(R.id.tv_friendEmail);
            TextView tvAdmin = row.findViewById(R.id.tv_admin);
            ImageView ivRemove = row.findViewById(R.id.iv_removeMember);
            ImageView ivBtnCall = row.findViewById(R.id.btn_call);


            tvFriendName.setText(names.get(position));
            tvFriendEmail.setText(emails.get(position));

            if (names.get(position).equals("me")) {
                ivBtnCall.setVisibility(View.GONE);
                ivRemove.setVisibility(View.GONE);
                if (amIAdmin) {
                    tvAdmin.setText(R.string.admin);
                    tvAdmin.setBackgroundResource(R.drawable.design_text_view_border_green);
                }
            } else {
                if (isAdmin.get(position)) {
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


            if (amIAdmin) {
//                btnLeaveDeleteGtoup.setText(R.string.delete_group);
            }
//            else {
//                tvAdmin.setText(" ");
//                ivRemove.setImageResource(0);
//            }

            ivBtnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "call " + names.get(position), Toast.LENGTH_SHORT).show();
                }
            });

            return row;
        }
    }
}
