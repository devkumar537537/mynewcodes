package com.example.qrcodescannerexample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private lateinit var movetogeneratecode: Button
    private lateinit var movetoBarCode: Button
    var pemrisonssoino = arrayOf(Manifest.permission.INTERNET, Manifest.permission.CAMERA)
    var requescod = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(pemrisonssoino, requescod)
            }
        }
        movetoBarCode = findViewById(R.id.btnScanBarCode)
        movetogeneratecode = findViewById(R.id.moveto_generate)
        movetogeneratecode.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    QrcodeGenerateActivity::class.java
                )
            )
        })
        movetoBarCode.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    QrScanActivity::class.java
                )
            )
        })
    }
}