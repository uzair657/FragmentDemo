package com.uxi.fragmentsdemo.KotlinTheme

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
    }
}