package com.example.biometricexamplekotlin

import android.content.Context
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.P)
class MyCallbacks(var context: Context) :
    BiometricPrompt.AuthenticationCallback() {
    var commonfucntions: CommonFunctions
    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        super.onAuthenticationError(errorCode, errString)
        commonfucntions.notifiyuser("error $errorCode")
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        commonfucntions.notifiyuser("Successfully verified")
        val intent = Intent(context.applicationContext, SecondActivty::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        commonfucntions.notifiyuser("Wrong Information")
    }

    init {
        commonfucntions = CommonFunctions(context)
    }
}