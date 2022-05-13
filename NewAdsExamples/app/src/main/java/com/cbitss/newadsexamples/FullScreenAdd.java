package com.cbitss.newadsexamples;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class FullScreenAdd extends AppCompatActivity {
private InterstitialAd interstitialAds;
  private   AdRequest adRequest;
    private static final String TAG = "FullScreenAdd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_add);
        MobileAds.initialize(getApplicationContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                 adRequest =new AdRequest.Builder().build();
                loadadd();
            }
        });
    }

    private void loadadd() {
//        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
//                new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        // The mInterstitialAd reference will be null until
//                        // an ad is loaded.
//                        mInterstitialAd = interstitialAd;
//                        Log.i(TAG, "onAdLoaded");
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error
//                        Log.i(TAG, loadAdError.getMessage());
//                        mInterstitialAd = null;
//                    }
//                });
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
               interstitialAds  = interstitialAd;
               if(interstitialAds != null)
               {
                   interstitialAds.show(FullScreenAdd.this);
//                   mInterstitialAds.setFullScreenContentCallback(new FullScreenContentCallback(){
//
//                       @Override
//                       public void onAdDismissedFullScreenContent() {
//                           super.onAdDismissedFullScreenContent();
//                           Toast.makeText(FullScreenAds.this, "Ad is closed now", Toast.LENGTH_SHORT).show();
//                       }
//                   });

               }
                Log.i(TAG, "onAdLoaded");

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

            }
        });
    }
}