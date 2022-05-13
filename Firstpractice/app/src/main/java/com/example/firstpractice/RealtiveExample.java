package com.example.firstpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class RealtiveExample extends AppCompatActivity {
    private static final String TAGG = "RelativeExample";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtive_example);
        Log.e(TAGG, "onCreate: " );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAGG, "onStart: " );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAGG, "onDestroy: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAGG, "onRestart: ");
    }
}