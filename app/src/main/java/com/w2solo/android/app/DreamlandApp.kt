package com.w2solo.android.app

import android.app.Application
import com.w2solo.android.BuildConfig
import com.w2solo.android.app.account.AccountManager
import com.w2solo.android.module.keyvalue.KV
import com.w2solo.android.sdks.BuglyHelper
import com.w2solo.android.utils.AppLog

class DreamlandApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppHolder.init(this)
        AppLog.setEnabled(BuildConfig.DEBUG)
        initSDKs()
    }

    private fun initSDKs() {
        //init kv
        KV.init(this) { "${AccountManager.getInstance().loginUserID}" }
        //init account
        AccountManager.getInstance()
        BuglyHelper.init(this)
    }
}