package com.myexample.callrecorder;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db=new DatabaseHandler(this);
    final static String TAGMA="Main Activity";
    RecordAdapter rAdapter;
    RecyclerView recycler;
    List<CallDetails> callDetailsList;
    boolean checkResume=false;
    String[] permissions = {Manifest.permission.ACCESS_MEDIA_LOCATION, WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO, READ_EXTERNAL_STORAGE,};
    int permissioncode = 124;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(SDK_INT > Build.VERSION_CODES.M)
        {
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(permissions,permissioncode);
            }
        }

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        pref.edit().putInt("numOfCalls",0).apply();




    }



    protected void onPause()
    {
        super.onPause();
        SharedPreferences pref3=PreferenceManager.getDefaultSharedPreferences(this);
        if(pref3.getBoolean("pauseStateVLC",false)) {
            checkResume = true;
            pref3.edit().putBoolean("pauseStateVLC",false).apply();
        }
        else
            checkResume=false;
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        MenuItem item=menu.findItem(R.id.mySwitch);

        View view = getLayoutInflater().inflate(R.layout.switch_layout,null,false) ;

        final SharedPreferences pref1= PreferenceManager.getDefaultSharedPreferences(this);

        SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.switchCheck);
        switchCompat.setChecked(pref1.getBoolean("switchOn",true));
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Log.d("Switch", "onCheckedChanged: " +isChecked);
                    Toast.makeText(getApplicationContext(), "Call Recorder ON", Toast.LENGTH_LONG).show();
                    pref1.edit().putBoolean("switchOn",isChecked).apply();
                }else{
                    Log.d("Switch", "onCheckedChanged: " +isChecked);
                    Toast.makeText(getApplicationContext(), "Call Recorder OFF", Toast.LENGTH_LONG).show();
                    pref1.edit().putBoolean("switchOn",isChecked).apply();
                }
            }
        });
        item.setActionView(view);
        return true;
    }
    public void setUi()
    {
        recycler=(RecyclerView) findViewById(R.id.recyclerView);
        callDetailsList=new DatabaseManager(this).getAllDetails();

        for(CallDetails cd:callDetailsList)
        {
            String log="Phone num : "+cd.getNum()+" | Time : "+cd.getTime1()+" | Date : "+cd.getDate1();
            Log.d("Database ", log);
        }

        Collections.reverse(callDetailsList);
        rAdapter=new RecordAdapter(callDetailsList,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(rAdapter);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!Utils.isPermissionGranted(this))
        {
            new AlertDialog.Builder(this)
                    .setTitle("All Files permission")
                    .setMessage("Due to android 11 the file permission required by the app at runtime ok")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                             takepermssion();
                        }
                    })
                    .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else
        {
            if(checkResume==false) {
                setUi();
                // this.callDetailsList=new DatabaseManager(this).getAllDetails();
                rAdapter.notifyDataSetChanged();
            }
            Toast.makeText(getApplicationContext(), "permission already granted here", Toast.LENGTH_SHORT).show();
        }
    }

    private void takepermssion() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R)
        {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                Uri uri = Uri.fromParts("package",getPackageName(),null);
                intent.setData(uri);
              //  startActivityForResult(intent,1122);
                requestpermissionlanucher.launch(intent);
            }catch (Exception e)
            {
                e.printStackTrace();
                Intent intent =new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                requestpermissionlanucher.launch(intent);
               // startActivityForResult(intent,1122);
            }
        }else
        {
            if(SDK_INT > Build.VERSION_CODES.M)
            {
                if(ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(permissions,permissioncode);
                }
            }
        }
    }
    ActivityResultLauncher<Intent> requestpermissionlanucher= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                 if(result.getResultCode() == Activity.RESULT_OK)
                 {
                     Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_SHORT).show();
                 }
                }
            }
    );
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0)
        {
            if(requestCode == 101)
            {
                boolean readExt = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if(!readExt)
                {
                    takepermssion();
                }
            }
        }
    }


    private String getpathdirectory()
    {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicdirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicdirectory,"testrecordingfile "+".mp3");
        return file.getPath();

    }
}