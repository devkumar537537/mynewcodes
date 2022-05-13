package com.example.myapplicationwatch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.myapplicationwatch.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private TextView mTextView;
private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMainBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());


    }
}