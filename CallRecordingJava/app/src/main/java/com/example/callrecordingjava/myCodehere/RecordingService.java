package com.example.callrecordingjava.myCodehere;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

public class RecordingService  extends Service {
    MediaRecorder mediaRecorder;
    private boolean recordingstart;
    private File file;
    String path = "/sdcard/alarms";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        Date date =new Date();
        //CharSequence sdf = DateFormat.getDateInstance().format("MM-dd-yy-hh-mm-ss",date.getTime());
       mediaRecorder =new MediaRecorder();
       mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);





        return super.onStartCommand(intent, flags, startId);
    }
}
