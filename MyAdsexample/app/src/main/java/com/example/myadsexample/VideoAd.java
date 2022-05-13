package com.example.myadsexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class VideoAd extends AppCompatActivity {
    AdRequest adRequest;
    RewardedAd rewardedAd;
    private static final String TAG = "VideoAd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_ad);
        MobileAds.initialize(getApplicationContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Toast.makeText(getApplicationContext(), "device is ready for load ads", Toast.LENGTH_SHORT).show();

            }
        });

        Button button = findViewById(R.id.buttonv);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adRequest = new AdRequest.Builder().build();
                loadvideoadd();
            }
        });
    }

    private void loadvideoadd() {
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                rewardedAd = rewardedAd;
                if(rewardedAd != null)
                {
                    Activity activitycontext = VideoAd.this;

                    rewardedAd.show(activitycontext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            Log.e(TAG, "The user earned the reward" );
                            int rewardamount = rewardItem.getAmount();
                            String rewardTyep = rewardItem.getType();
                            Log.e(TAG, "amount is => "+rewardamount +" and type is => "+rewardTyep );
                        }
                    });
                    rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            Log.e(TAG, "onAdDismissedFullScreenContent: ad closed" );
                        }
                    });
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e(TAG, "onAdFailedToLoad: "+loadAdError );


            }
        });

    }
}