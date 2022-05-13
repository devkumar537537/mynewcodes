package com.example.broadcasterinkotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class GlobarReceiver : BroadcastReceiver(){


    override fun onReceive(context: Context?, intent: Intent?) {

        var actionstring = intent!!.action
        Toast.makeText(context,"action "+actionstring,Toast.LENGTH_LONG).show()
        if(actionstring.equals("android.intent.action.AIRPLANE_MODE")){
            Toast.makeText(context, "You implemented global recevier", Toast.LENGTH_SHORT).show();
        }else if(actionstring.equals("android.intent.action.TIMEZONE_CHANGED")){
            Toast.makeText(context, "Time zone chan ged", Toast.LENGTH_SHORT).show();
        }
    }

}