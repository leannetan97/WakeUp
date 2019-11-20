package com.wakeup.wakeup.UserLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wakeup.wakeup.R;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
    }
}
