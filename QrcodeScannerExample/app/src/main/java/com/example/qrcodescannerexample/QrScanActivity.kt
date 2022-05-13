package com.example.qrcodescannerexample

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

import java.io.IOException

@SuppressLint("MissingPermission")
class QrScanActivity : AppCompatActivity() {
 lateinit   var surfaceView: SurfaceView
   lateinit var texBArcodevalue: TextView
 lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraSource: CameraSource
  lateinit  var btnaciton: Button
    var intendData = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scan)
        bindview()
        btnaciton.setOnClickListener {
            if (intendData.length > 0) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(intendData)))
            }
        }
    }

    private fun bindview() {
        surfaceView = findViewById(R.id.surfaceview)
        btnaciton = findViewById(R.id.btnaction)
        texBArcodevalue = findViewById(R.id.textbarcodevalue)
    }

    override fun onResume() {
        super.onResume()
        intialisecamerasource()
    }

    private fun intialisecamerasource() {
        Toast.makeText(this, "Initialisation start", Toast.LENGTH_SHORT).show()
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setAutoFocusEnabled(true)
            .setRequestedPreviewSize(1920, 1080)
            .build()
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    cameraSource.start(surfaceView.holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(applicationContext, "Release Resorces here", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun receiveDetections(detections: Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    texBArcodevalue.post {
                        btnaciton.text = "LAUNCH URL"
                        intendData = barcodes.valueAt(0).displayValue
                        texBArcodevalue.text = intendData
                    }
                }
            }
        })
    }
}