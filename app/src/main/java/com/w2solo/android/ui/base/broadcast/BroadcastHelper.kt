package com.w2solo.android.ui.base.broadcast

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.w2solo.android.app.AppHolder
import com.w2solo.android.utils.Logger

object BroadcastHelper {

    val TAG = BroadcastHelper::class.java.simpleName

    /**
     * 发送广播
     *
     * @param action
     */
    fun sendBroadcast(action: String?) {
        sendBroadcast(Intent(action))
    }

    /**
     * 发送广播
     *
     * @param action
     */
    fun sendBroadcast(action: Intent) {
        val context: Context = AppHolder.getApplication()!!
        Logger.d(TAG, "发送广播 " + action.action + "    " + action.extras)
        LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(action))
    }

    /**
     * UI注册广播监听事件
     *
     * @param receiver
     * @param actions
     */
    fun registerBroadcast(receiver: BaseBroadcastReceiver?, actions: Array<out String>?) {
        if (receiver == null || actions == null) {
            Logger.e(TAG, "receiver is null")
            return
        }
        val context: Context = AppHolder.getApplication()!!
        val bm = LocalBroadcastManager.getInstance(context)
        for (i in actions.indices) {
            bm.registerReceiver(receiver, IntentFilter(actions[i]))
            if (Logger.isEnabled()) {
                Logger.w(TAG, "register broadcast " + actions[i])
            }
        }
    }

    /**
     * 取消广播接收
     *
     * @param receiver
     */
    fun unregisterBroadcast(receiver: BaseBroadcastReceiver?) {
        if (receiver == null) {
            return
        }
        val context: Context = AppHolder.getApplication()!!
        val bm = LocalBroadcastManager.getInstance(context)
        bm.unregisterReceiver(receiver)
    }
}