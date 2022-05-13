package com.cbitss.servicesexamplelatest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        switch (intent.getAction())
        {
            case "play":
                MyBoundedActivity.myBoundedService.startmedia();
                Toast.makeText(context, "play is detedted", Toast.LENGTH_SHORT).show();
                break;
            case "pause":
                MyBoundedActivity.myBoundedService.pausemedia();
                Toast.makeText(context, "song is stop", Toast.LENGTH_SHORT).show();
                break;
            case "exit":
                MyBoundedActivity.myBoundedService.stopForeground(true);
                MyBoundedActivity.myBoundedService = null;
        }
    }
}
