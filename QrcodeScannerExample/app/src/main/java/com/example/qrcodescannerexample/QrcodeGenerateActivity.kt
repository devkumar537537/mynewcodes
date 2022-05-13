package com.example.qrcodescannerexample

import android.graphics.Bitmap
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.google.zxing.WriterException


class QrcodeGenerateActivity : AppCompatActivity() {
    private lateinit var qrCodeIV: ImageView
    private lateinit var dataEdt: EditText
    private lateinit var generateQrBtn: Button
    var bitmap: Bitmap? = null
    var qrgEncoder: QRGEncoder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_generate)
        qrCodeIV = findViewById(R.id.idIVQrcode)
        dataEdt = findViewById(R.id.idEdt)
        generateQrBtn = findViewById(R.id.idBtnGenerateQR)
        generateQrBtn.setOnClickListener(View.OnClickListener {
            val datatext = dataEdt.getText().toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(datatext)) {
                Toast.makeText(
                    applicationContext,
                    "Enter some text to generate QR Code",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val manager = getSystemService(WINDOW_SERVICE) as WindowManager
                val display = manager.defaultDisplay
                val point = Point()
                display.getSize(point)
                val width = point.x
                val height = point.y
                var dimen = if (width < height) width else height
                dimen = dimen * 3 / 4
                qrgEncoder = QRGEncoder(datatext, null, QRGContents.Type.TEXT, dimen)
                try {
                    bitmap = qrgEncoder!!.encodeAsBitmap()
                } catch (e: WriterException) {
                    e.printStackTrace()
                }
                qrCodeIV.setImageBitmap(bitmap)
            }
        })
    }
}