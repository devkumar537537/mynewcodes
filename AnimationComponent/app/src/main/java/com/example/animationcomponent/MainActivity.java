package com.example.animationcomponent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button rotatebtn;
    ImageView imageView;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rotatebtn =findViewById(R.id.roatebtn);
        imageView = findViewById(R.id.imageview);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.roundanimation);
        rotatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageView.startAnimation(animation);

            }
        });

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Toast.makeText(getApplicationContext(), "animationstart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(), "animation end", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Toast.makeText(getApplicationContext(), "animation repeat", Toast.LENGTH_SHORT).show();
            }
        });

    }
}