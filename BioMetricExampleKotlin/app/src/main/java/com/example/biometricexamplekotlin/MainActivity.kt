package com.example.biometricexamplekotlin

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

@RequiresApi(api = Build.VERSION_CODES.P)
class MainActivity : AppCompatActivity() {
    var permissions = arrayOf(Manifest.permission.USE_BIOMETRIC)
    var requestcode = 123
 private  lateinit var bioMatricCallbacksExample: MyCallbacks
  private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var cancellationSignal: CancellationSignal
  private lateinit var commonfucntions: CommonFunctions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bioMatricCallbacksExample = MyCallbacks(applicationContext)
        commonfucntions = CommonFunctions(applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.USE_BIOMETRIC
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(permissions, requestcode)
            } else {
                biometricPrompt = BiometricPrompt.Builder(this)
                    .setTitle("Contace Data")
                    .setSubtitle("Authentication required")
                    .setDescription("We will use this information for accessing your personal data")
                    .setNegativeButton("Cancel", mainExecutor,
                        { dialog, which -> commonfucntions.notifiyuser("User Cancel the dialog") })
                    .build()
                biometricPrompt.authenticate(getcanclesingle(), mainExecutor, bioMatricCallbacksExample)
            }
        }
    }

    fun getcanclesingle(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal!!.setOnCancelListener { commonfucntions.notifiyuser("cancel signal activitated") }
        return cancellationSignal
    }
}