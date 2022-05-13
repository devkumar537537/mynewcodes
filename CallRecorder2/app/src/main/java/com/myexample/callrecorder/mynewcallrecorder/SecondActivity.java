package com.myexample.callrecorder.mynewcallrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.myexample.callrecorder.MainActivity;
import com.myexample.callrecorder.R;

public class SecondActivity extends AppCompatActivity {
Button servicestop,servicestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        servicestop= findViewById(R.id.stopservice);
        servicestart =findViewById(R.id.startservicebtn);
        servicestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startService(new Intent(getApplicationContext(),RecordingService.class));
            }
        });
        servicestop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(SecondActivity.this,RecordingService.class));
            }
        });
    }
}