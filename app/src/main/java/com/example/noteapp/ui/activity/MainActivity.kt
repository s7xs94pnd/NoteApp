package com.example.noteapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.noteapp.R
import com.example.noteapp.utils.PreferenceHelper

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        checkOnboardingStatus()
    }

    private fun setupNavigation() {
        // Инициализация NavController из NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
    private fun checkOnboardingStatus() {
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)

        if (sharedPreferences.onBoardShown) {
            navController.navigate(R.id.action_onBoardFragment_to_noteFragment)
        }
    }
}