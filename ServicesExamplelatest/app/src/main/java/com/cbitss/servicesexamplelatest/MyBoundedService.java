package com.cbitss.servicesexamplelatest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyBoundedService extends Service {
    MediaPlayer mediaPlayer;
    boolean res = false;

public static final String CHANNELID="something";



   IBinder mybinder = new MyLocalBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mybinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager notificationManager =(NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {

            NotificationChannel nfchannel = new NotificationChannel(CHANNELID,"sometghing",NotificationManager.IMPORTANCE_DEFAULT);
            nfchannel.setDescription("Music Player");
            nfchannel.enableLights(true);
            nfchannel.enableVibration(true);
            nfchannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
            notificationManager.createNotificationChannel(nfchannel);
        }
        Intent playintent = new Intent(getBaseContext(),NotificationReceiver.class).setAction("play");
        PendingIntent playpendingintent = PendingIntent.getBroadcast(getBaseContext(),0,playintent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pauseintent = new Intent(getBaseContext(),NotificationReceiver.class).setAction("pause");
        PendingIntent pausependingintent = PendingIntent.getBroadcast(getBaseContext(),0,pauseintent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this,CHANNELID)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle("Service")
                .setContentText("Service is running")
                .setAutoCancel(true)
                .addAction(android.R.drawable.ic_media_play,"Play",playpendingintent)
                .addAction(android.R.drawable.ic_media_pause,"Pause",pausependingintent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        startForeground(12,notification.build());
        return START_STICKY;
    }

    public class MyLocalBinder extends Binder {
        MyBoundedService getService() {
            return MyBoundedService.this;
        }
    }
    public String getCurrentTime() {
        SimpleDateFormat dateformat =
                new SimpleDateFormat("HH:mm:ss MM/dd/yyyy",
                        Locale.US);
        return (dateformat.format(new Date()));

    }
    public void startmedia()
    {

        mediaPlayer = MediaPlayer.create(this,R.raw.song);

    mediaPlayer.start();



    }
    public void pausemedia()
    {
        if( res == true)
        {
            mediaPlayer.pause();
            res = false;
        }else
        {
            mediaPlayer.start();
            res = true;
        }
    }
    public void reset(){
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
