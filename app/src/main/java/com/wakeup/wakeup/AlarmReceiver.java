package com.wakeup.wakeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import com.wakeup.wakeup.ObjectClass.Alarm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class AlarmReceiver extends BroadcastReceiver {

    private Context mContext;
    private Alarm alarm;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());

        mContext = context;

        // Get the Alarm extra
        alarm = (intent.getBundleExtra("alarmBundle")).getParcelable("alarm");

        // Move to alarmPopUp
//        setAlarmRing();
//        setVibration();
        navigateToAlarmPopUp();
    }

//    private void setAlarmRing(){
//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alarmUri == null) {
//            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//        }
//
//        MediaPlayer mediaPlayer = new MediaPlayer();
//        if (Build.VERSION.SDK_INT >= 21) {
//            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
//        } else {
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
//        }
//        mediaPlayer.setLooping(true);
//        try {
//            mediaPlayer.setDataSource(mContext, alarmUri);
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.start();
//    }
//
//    private void setVibration(){
//        // Add Vibrate
//        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
//        long[] pattern = {0, 1000, 500, 1000, 500};
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0));
//        } else {
//            //deprecated in API 26
//            vibrator.vibrate(pattern, 0);
//        }
//    }

    private void navigateToAlarmPopUp(){
        Intent alarmPopUp = new Intent(mContext, AlarmPopUp.class);
        alarmPopUp.putExtra("AlarmData",alarm);
        alarmPopUp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mContext.startActivity(alarmPopUp);
    }
}