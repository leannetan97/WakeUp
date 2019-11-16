package com.wakeup.wakeup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void click(View v)
    {
        Intent GoToRegPage = new Intent(this, SignUp.class);
        startActivity(GoToRegPage);
    }

    public void alarm(View v)
    {
        Intent GoToAlarmPage = new Intent(this, AlarmPopUp.class);
        startActivity(GoToAlarmPage);
    }

    public void forgot(View v)
    {
//        Intent GoToForgotPage = new Intent(this, Forgot_Password.class);
//        startActivity(GoToForgotPage);
    }
}
