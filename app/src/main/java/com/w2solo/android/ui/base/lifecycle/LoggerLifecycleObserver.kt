package com.w2solo.android.ui.base.lifecycle

import android.util.Log
import androidx.lifecycle.LifecycleOwner

class LoggerLifecycleObserver : DefaultLifecycleObserver() {

    override fun onCreate(owner: LifecycleOwner?) {
        super.onCreate(owner)
        logLife(owner, "onCreate")
    }

    override fun onStart(owner: LifecycleOwner?) {
        super.onStart(owner)
        logLife(owner, "onStart")
    }

    override fun onResume(owner: LifecycleOwner?) {
        super.onResume(owner)
        logLife(owner, "onResume")
    }

    override fun onPause(owner: LifecycleOwner?) {
        super.onPause(owner)
        logLife(owner, "onPause")
    }

    override fun onStop(owner: LifecycleOwner?) {
        super.onStop(owner)
        logLife(owner, "onStop")
    }

    override fun onDestroy(owner: LifecycleOwner?) {
        super.onDestroy(owner)
        logLife(owner, "onDestroy")
    }

    private fun logLife(owner: LifecycleOwner?, name: String) {
        Log.d(
            LoggerLifecycleObserver::class.java.simpleName,
            owner!!.javaClass.simpleName + "====$name===="
        )
    }
}