package com.cbitss.servicesexamplelatest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MySerivice extends Service {
    MediaPlayer mediaPlayer;
public static final String CHANNELID="Somethign";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }


    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();
        mediaPlayer = MediaPlayer.create(this,R.raw.song);
        mediaPlayer.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager notificationManager =(NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {

            NotificationChannel nfchannel = new NotificationChannel(CHANNELID,getString(R.string.Channel_Name),NotificationManager.IMPORTANCE_DEFAULT);
            nfchannel.setDescription("Music Player");
            nfchannel.enableLights(true);
            nfchannel.enableVibration(true);
            nfchannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
            notificationManager.createNotificationChannel(nfchannel);
        }



        NotificationCompat.Builder notification = new NotificationCompat.Builder(this,CHANNELID)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle("Service")
                .setContentText("Service is running")
                .setAutoCancel(true)

                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        startForeground(12,notification.build());
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Service Stope", Toast.LENGTH_SHORT).show();

        mediaPlayer.stop();
    }
        public void onpause()
        {
            mediaPlayer.pause();
        }
}
