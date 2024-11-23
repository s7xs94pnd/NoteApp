package com.example.noteapp

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.noteapp.data.db.AppDataBase
import com.example.noteapp.utils.PreferenceHelper
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.math.log

class App : Application() {
    companion object {
        var appDataBase: AppDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)
        databaseExist()
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }

            // Получение токена
            val token = task.result
            Log.d("FCM", "FCM token: $token")
        }
    }

    private fun databaseExist(): AppDataBase? {
        if (appDataBase == null) {
            appDataBase = applicationContext.let { context ->
                Room.databaseBuilder(context, AppDataBase::class.java, "note.database")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
        }
        return appDataBase
    }
}