package com.wakeup.wakeup.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.wakeup.wakeup.R;

import java.util.Random;

public class GameMath extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_math);

        setNewNumbers();
    }
    int value1;
    int value2;
    int correctPoint;



    public void setNewNumbers() {
        Random r = new Random();
        value1 = r.nextInt(99);
        value2 = r.nextInt(99);


        TextView Number1 = (TextView) findViewById(R.id.Number1);
        Number1.setText("" + value1);
        TextView Number2 = (TextView) findViewById(R.id.Number2);
        Number2.setText("" + value2);



    }

    public void onSubmitClick (View view){


        TextView Answer = findViewById(R.id.Result);
        EditText Attempt = findViewById(R.id.Attempt);

        int userAnswer = Integer.parseInt(Attempt.getText().toString());
        if (userAnswer == value1 + value2) {
            Answer.setText("Correct!");
            correctPoint++;


        } else {
            Answer.setText("Wrong! Try again");
        }

        setNewNumbers();


        if(correctPoint==3){
            finish();
        }
    }

}
