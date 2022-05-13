
package com.example.animationcomponent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Starshower extends AppCompatActivity {
Button begainshower;
ImageView star;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starshower);
        begainshower = findViewById(R.id.showerButton);
        star = findViewById(R.id.star);
        begainshower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = 1;
                while (a <= 10) {
                    for (int i = 1; i < 20; i++) {

                        shower();
                    }
                    a++;
                }
            }
        });
    }

    private void shower() {
        // Create a new star view in a random X position above the container.
        // Make it rotateButton about its center as it falls to the bottom.

        // Local variables
        ViewGroup container =(ViewGroup) star.getParent();

        int containerW =container.getWidth();
        int containerH =container.getHeight();

        float starW = star.getWidth();
        float starH = star.getHeight();

        // Create the new star (an ImageView in layout holding drawable star image)
        // and add it to the container


        AppCompatImageView newStar = new AppCompatImageView(this);
        newStar.setImageResource(R.drawable.ic_star);
        newStar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT));
    container.addView(newStar);


        // Scale the view randomly between 10-160% of its default size

        newStar.setScaleX((float) (Math.random() * 1.5f + .1f));
        newStar.setScaleY(newStar.getScaleX());
        starW *= newStar.getScaleX();
        starH *= newStar.getScaleY();

        // Position the view at a random place between
        // the left and right edges of the container

        newStar.setTranslationX((float) (Math.random() * containerW - starW /2));

        // Create an animator that moves the view from a starting position right about the container
        // to an ending position right below the container. Set an accelerate interpolator to give
        // it a gravity/falling feel

        ObjectAnimator mover =  ObjectAnimator.ofFloat(newStar,View.TRANSLATION_Y,-starH,containerH+starH);
        mover.setInterpolator(new AccelerateInterpolator(1f));

        // Create an animator to rotateButton the
        // view around its center up to three times

        ObjectAnimator rotator = ObjectAnimator.ofFloat(newStar,View.ROTATION, (float) (Math.random() * 1080));
             rotator.setInterpolator(new LinearInterpolator());


        // Use an AnimatorSet to play the falling and
        // rotating animators in parallel for a duration
        // of a half-second to two seconds



        AnimatorSet set = new AnimatorSet();
        set.playTogether(new Animator[]{(Animator)mover, (Animator)rotator});
        set.setDuration((long)(Math.random() * (double)1500 + (double)500));
        set.addListener((Animator.AnimatorListener)(new AnimatorListenerAdapter() {
            public void onAnimationEnd(@Nullable Animator animation) {
                container.removeView((View)newStar);
            }
        }));
        set.start();


    }


}