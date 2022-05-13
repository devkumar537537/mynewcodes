package com.example.biometricexamplekotlin

import android.content.Context
import android.widget.Toast

class CommonFunctions(var context: Context) {
    fun notifiyuser(title: String?) {
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
    }
}