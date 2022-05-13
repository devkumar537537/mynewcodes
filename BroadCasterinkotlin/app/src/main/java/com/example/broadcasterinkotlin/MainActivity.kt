package com.example.broadcasterinkotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Switch

class MainActivity : AppCompatActivity() {
    lateinit var wifiswitch:Switch
    lateinit var globarReceiver: GlobarReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wifiswitch = findViewById(R.id.wifiswitch)
           globarReceiver = GlobarReceiver()
        var wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE)

wifiswitch.setOnCheckedChangeListener { buttonView, isChecked ->

    if(isChecked){
        //wifi enabling code
    }else{
        //wifi on code
    }
}
    }


    private var broadcastReceiver:BroadcastReceiver =object :BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent?) {
 var actionwifit = intent!!.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN)
       when (actionwifit)
       {
           WifiManager.WIFI_STATE_ENABLED->{
               wifiswitch.isChecked = true
                  wifiswitch.text = "Wifi Is On"
           }
           WifiManager.WIFI_STATE_DISABLED->{
               wifiswitch.isChecked = false
      wifiswitch.text = "Wifi Is Off"
           }
       }

        }

    }

    override fun onStart() {
        super.onStart()


        var intentenfilter  = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        var intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(globarReceiver,intentFilter)
        registerReceiver(broadcastReceiver,intentenfilter)
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
        unregisterReceiver(broadcastReceiver)
    }


}