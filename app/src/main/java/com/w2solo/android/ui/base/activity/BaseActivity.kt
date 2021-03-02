package com.w2solo.android.ui.base.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.annotation.IdRes
import com.w2solo.android.ui.base.broadcast.BaseBroadcastReceiver
import com.w2solo.android.ui.base.broadcast.BroadcastHelper
import java.util.*

abstract class BaseActivity : BaseLifeCycleActivity() {

    private var broadcastReceivers: ArrayList<BaseBroadcastReceiver>? = null

    fun <T : View> fview(@IdRes id: Int): T {
        return findViewById(id)
    }

    fun <T : View> fview(@IdRes id: Int, clickListener: View.OnClickListener?): T? {
        val view: T = findViewById(id)
        view.setOnClickListener(clickListener)
        return view
    }

    override fun onDestroy() {
        clearBroadcastReceiver()
        super.onDestroy()
    }

    /**
     * Activity退出时，清除所有注册得广播
     */
    private fun clearBroadcastReceiver() {
        if (broadcastReceivers == null) {
            return
        }
        for (receiver in broadcastReceivers!!) {
            BroadcastHelper.unregisterBroadcast(receiver)
        }
        broadcastReceivers!!.clear()
        broadcastReceivers = null
    }

    /**
     * 注册广播，使用了 [androidx.localbroadcastmanager.content.LocalBroadcastManager] 来处理进程内部的广播通讯
     *
     * @param actions 要处理的操作
     */
    protected fun registerBroadcastReceiver(
        receiver: BaseBroadcastReceiver?, actions: Array<String>): Boolean {
        if (receiver == null) {
            return false
        }
        if (broadcastReceivers == null) {
            broadcastReceivers = ArrayList()
        }
        if (broadcastReceivers!!.contains(receiver)) {
            return false
        }
        broadcastReceivers!!.add(receiver)
        BroadcastHelper.registerBroadcast(receiver, actions)
        return true
    }

    fun thisActivity(): Activity {
        return this
    }

    protected fun startActivity(cls: Class<*>?) {
        startActivity(Intent(thisActivity(), cls))
    }
}