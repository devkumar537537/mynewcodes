package com.example.biometricauthentication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;

@RequiresApi(api = Build.VERSION_CODES.P)
public class MainActivity extends AppCompatActivity {
String[] permissions = {Manifest.permission.USE_BIOMETRIC};
int requestcode = 123;
    BiometricPrompt biometricPrompt;
    AuthenitcationCallbacks authenticationCallback;
CancellationSignal cancellationSignal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

authenticationCallback = new AuthenitcationCallbacks(getApplicationContext());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        {
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(permissions,requestcode);
            }else
            {
                 biometricPrompt = new BiometricPrompt.Builder(this)
                        .setTitle("User")
                        .setSubtitle("Authentication is requried")
                        .setDescription("we will use this information for device hack")
                         .setNegativeButton("Cancel", this.getMainExecutor(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              Toast.makeText(getApplicationContext(),"user canceled the box",Toast.LENGTH_SHORT).show();
                            }
                        }).build();


            }


            biometricPrompt.authenticate(getCancellationSignal(),getMainExecutor(),authenticationCallback);
        }
    }

    private CancellationSignal getCancellationSignal()
    {
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "cancelled by users", Toast.LENGTH_SHORT).show();
            }
        });
        return cancellationSignal;
    }


}