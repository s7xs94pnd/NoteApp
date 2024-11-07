package com.example.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {

    private lateinit var pref:SharedPreferences

    fun unit(context: Context){
        pref = context.getSharedPreferences("shared",Context.MODE_PRIVATE)
    }

    var onBoardShown:Boolean
        get()=pref.getBoolean("onBoard",false)
        set(value)=pref.edit().putBoolean("onBoard",value).apply()
}