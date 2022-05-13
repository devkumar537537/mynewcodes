package com.example.myadsexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class FullScrrenAd extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private static final String TAG = "FullScreenAd";
    AdRequest adRequest;
    boolean res = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_scrren_ad);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Toast.makeText(getApplicationContext(), "device is ready for load ads", Toast.LENGTH_SHORT).show();

                adRequest = new AdRequest.Builder().build();
                loadadd();
            }
        });





    }

    private void loadadd() {

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);

                mInterstitialAd = interstitialAd;

                if(mInterstitialAd != null)
                {
                    mInterstitialAd.show(FullScrrenAd.this);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            Log.e(TAG, "onAdFailedToShowFullScreenContent: ");
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                            Log.e(TAG, "onAdShowedFullScreenContent: ");
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            Log.e(TAG, "onAdDismissedFullScreenContent: ");
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                            Log.e(TAG, "onAdImpression: ");
                        }

                        @Override
                        public void onAdClicked() {
                            super.onAdClicked();
                            Log.e(TAG, "onAdClicked: ");
                        }
                    });
                }
                Log.e(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.i(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });

    }
}