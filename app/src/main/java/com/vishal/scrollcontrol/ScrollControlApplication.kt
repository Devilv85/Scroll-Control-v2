package com.vishal.scrollcontrol

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for Scroll Control v2
 * Minimalist digital wellness app that blocks YouTube Shorts and Instagram Reels
 */
@HiltAndroidApp
class ScrollControlApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
    }
}