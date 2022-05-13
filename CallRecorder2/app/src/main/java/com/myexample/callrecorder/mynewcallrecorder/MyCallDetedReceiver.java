package com.myexample.callrecorder.mynewcallrecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


public class MyCallDetedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Receiver Start");


        Bundle extras = intent.getExtras();
        String state = extras.getString(TelephonyManager.EXTRA_STATE);

        Toast.makeText(context, "Call detected(Incoming/Outgoing) " + state, Toast.LENGTH_SHORT).show();


            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

            } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

                    Intent reivToServ = new Intent(context, RecordingService.class);
                    context.startService(reivToServ);
            } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {


                    context.stopService(new Intent(context, RecordingService.class));


                }

            }

    }

