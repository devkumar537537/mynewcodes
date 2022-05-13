package com.example.biometricauthentication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

@RequiresApi(api = Build.VERSION_CODES.P)
public class AuthenitcationCallbacks extends BiometricPrompt.AuthenticationCallback {
    Context context;

    public AuthenitcationCallbacks(Context context) {
        this.context = context;
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        Toast.makeText(context.getApplicationContext(), "error" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        Toast.makeText(context.getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();

        Toast.makeText(context.getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
    }


}
