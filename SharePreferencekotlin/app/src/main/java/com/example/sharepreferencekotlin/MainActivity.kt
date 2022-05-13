package com.example.sharepreferencekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {
   lateinit var insertbtn: Button
   lateinit var showbtn: Button
   lateinit var deleltbtn: Button
   lateinit var movetorecyclerview: Button
   lateinit var showtext: TextView
   lateinit var editvalue: EditText
   lateinit var editkey: EditText
    lateinit var key: String
   lateinit var sharedoperation :SharedOperations
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        conecxml()
        sharedoperation = SharedOperations()
        insertbtn.setOnClickListener {
            key = editkey.text.toString()
            val value = editvalue.text.toString()
            sharedoperation.insertvalue(key, value, applicationContext)
        }
        showbtn.setOnClickListener {
            key = editkey.text.toString()
            val vlaue: String = sharedoperation.showvalue(key, applicationContext)!!
            showtext.text = vlaue
        }
        deleltbtn.setOnClickListener {
            key = editkey.text.toString()
            sharedoperation.deletekey(key, applicationContext)
        }
        movetorecyclerview.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    SecondActivity::class.java
                )
            )
        }
    }

    private fun conecxml() {
        insertbtn = findViewById(R.id.insertbtn)
        showbtn = findViewById(R.id.Showbtn)
        showtext = findViewById(R.id.valuetextview)
        editvalue = findViewById(R.id.value)
        editkey = findViewById(R.id.keyedit)
        deleltbtn = findViewById(R.id.deletebtn)
        movetorecyclerview = findViewById(R.id.movetoanotherbtn)
    }
}