package com.w2solo.android.app

import android.app.Application
import com.w2solo.android.BuildConfig
import com.w2solo.android.utils.Logger

class DreamlandApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AppHolder.init(this)
        Logger.setEnabled(BuildConfig.DEBUG)
    }

    private fun initSDKs() {}
}