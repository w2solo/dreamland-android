package com.w2solo.android.app

import android.app.Application

object AppHolder {

    private var application: Application? = null

    fun init(app: Application?) {
        application = app
    }

    fun getApplication(): Application? {
        return application
    }
}