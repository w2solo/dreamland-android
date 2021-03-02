package com.w2solo.android.app

import android.app.Application
import com.w2solo.android.utils.Logger

class DreamlandApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AppHolder.init(this)
        //TODO Logger init
//        Logger.setDebugable(BuildC)
    }

    private fun initSDKs() {}
}