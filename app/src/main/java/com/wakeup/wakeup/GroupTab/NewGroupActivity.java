package com.wakeup.wakeup.GroupTab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wakeup.wakeup.ListFriendsActivity;
import com.wakeup.wakeup.R;


public class NewGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        ActionBar ab = getSupportActionBar();
//        assert ab != null;
//        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.new_group_text);

        Button btnAddFromList = (Button)findViewById(R.id.btn_groupInvite);
        btnAddFromList.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent I = new Intent(NewGroupActivity.this, ListFriendsActivity.class);
                startActivity(I);
            }
        });



//        Toolbar tbNewGroup = findViewById(R.id.tb_NewGroup);
//        setSupportActionBar(tbNewGroup);
//        ActionBar ab = getSupportActionBar();
//
//        // Enable the Up button
////        assert ab != null;
//        ab.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_group_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_newGroupOK:
                Toast.makeText(this, "OK!", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

//    @Override
//    public boolean onNavigateUp() {
//        finish();
//        return true;
//
//    }
}
