package com.w2solo.android.ui.base.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.w2solo.android.utils.Logger

abstract class BaseBroadcastReceiver : BroadcastReceiver() {

    private val TAG = BaseBroadcastReceiver::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        /**
         * 广播事件根据Intent的action来处理，如果action为空则不处理
         */
        if (intent == null || TextUtils.isEmpty(intent.action)) {
            return
        }
        Logger.d(TAG, "收到广播 " + intent.action + "  " + intent.extras)
        handleAction(intent)
    }

    /**
     * 事件处理方法，只需重写此方法进行UI更新即可
     *
     * @param intent
     */
    abstract fun handleAction(intent: Intent)
}