package com.w2solo.android.sdks

import android.app.Application
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.crashreport.CrashReport
import com.w2solo.android.R
import com.w2solo.android.utils.AppLog

/**
 * TODO APPID 应该做成可配置
 */
object BuglyHelper {
    fun init(application: Application) {
        Beta.autoCheckAppUpgrade = false
        Beta.upgradeCheckPeriod = 86400 / 2
        Beta.largeIconId = R.mipmap.ic_launcher
        Beta.smallIconId = R.mipmap.ic_launcher
        Beta.defaultBannerId = R.mipmap.ic_launcher
        Beta.enableNotification = true
        CrashReport.initCrashReport(application, "e86e4644f7", AppLog.isEnabled())
    }
}