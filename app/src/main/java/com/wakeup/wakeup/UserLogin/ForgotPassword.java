package com.wakeup.wakeup.UserLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wakeup.wakeup.R;
import com.wakeup.wakeup.UserLogin.SignUp;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");
    }

    public void click(View v)
    {
        Intent GoToRegPage = new Intent(this, SignUp.class);
        startActivity(GoToRegPage);
    }
}
