package com.wakeup.wakeup.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;

import com.wakeup.wakeup.ObjectClass.FirebaseHelper;
import com.wakeup.wakeup.ObjectClass.Game;
import com.wakeup.wakeup.AlarmPopUp;
import com.wakeup.wakeup.ObjectClass.Alarm;
import com.wakeup.wakeup.R;

public class GameShake extends AppCompatActivity {



    public SensorManager sm;

    private long start;

    public float acelVal; // CURRENT ACCELERATION VALUE AND GRAVITY
    public float acelLast; // LAST ACCELERATION VALUE AND GRAVITY
    public float shake; // ACCELERATION VALUE differ from GRAVITY
    private Alarm alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_shake);

        start = System.currentTimeMillis();

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

        alarm = getIntent().getExtras().getParcelable("AlarmData");

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
    }

    public final SensorEventListener sensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;

            if (shake > 12) {
//                new FirebaseHelper().addScore(new Game(2, 10));
                long end = System.currentTimeMillis();
                float msec = end - start;
                float sec= msec/1000F;
                int minutes= (int) (sec/60F);

                new FirebaseHelper().addHistory(minutes);

                if(alarm.isGroup()){
                    new FirebaseHelper().setUserAwake(alarm.getGroupKey());

                    System.out.println("[DEBUG] isGroup: need update awakeStatus");
                    //Update the awake status
                }
                AlarmPopUp.stopAlarm();
                finishAffinity();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }


    };


}
