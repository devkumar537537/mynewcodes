package com.example.sharepreferencekotlin

import android.content.Context
import android.content.SharedPreferences

class SharedOperations {
     val MY_PREFERN_NAME = "com.example.sharepreferecnexample"
    fun insertvalue(key: String?, value: String?, context: Context) {
        val sharedPreferences = context.getSharedPreferences(MY_PREFERN_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun showvalue(key: String?, context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(MY_PREFERN_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "Nothing")
    }

    fun deletekey(key: String?, context: Context) {
        val settings = context.getSharedPreferences(MY_PREFERN_NAME, Context.MODE_PRIVATE)
        settings.edit().remove(key).commit()
    }
}
