package com.cbitss.newadsexamples;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class VideoAdd extends AppCompatActivity {
    AdRequest adRequest;
    Button buttonvideo;
    private RewardedAd mrewardedAd;
    private static final String TAG = "VideoAdd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_add);
         buttonvideo =findViewById(R.id.loadaddbtn);
        adRequest = new AdRequest.Builder().build();

        buttonvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadadd();

                if(mrewardedAd != null)
                {

                    Activity activitycontext = VideoAdd.this;

                    mrewardedAd.show(activitycontext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            Log.e(TAG, "The user earned the reward" );
                            int rewardamount = rewardItem.getAmount();
                            String rewardTyep = rewardItem.getType();
                            Log.e(TAG, "amount is => "+rewardamount +" and type is => "+rewardTyep );
                        }
                    });
//                    mrewardedAd.setFullScreenContentCallback(new FullScreenContentCallback(){
//                        @Override
//                        public void onAdDismissedFullScreenContent() {
//                            Toast.makeText(activitycontext, "Add dismissed", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
                    mrewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }

                        @Override
                        public void onAdClicked() {
                            super.onAdClicked();
                        }
                    });
                }else
                {
                    Toast.makeText(getApplicationContext(), "ad is not ready yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loadadd() {


        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                mrewardedAd = rewardedAd;

                Log.e(TAG, "onAdLoaded: ");

                Toast.makeText(VideoAdd.this, "Show the add now", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                Log.e(TAG, loadAdError.getMessage() );
            }
        });
    }
}