package com.w2solo.android.ui.base.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.w2solo.android.ui.base.lifecycle.LoggerLifecycleObserver

abstract class BaseLifeCycleActivity : AppCompatActivity(), LifecycleOwner {

    override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        addLifecycleObserver(LoggerLifecycleObserver())
        super.onCreate(savedInstanceState, persistentState)
    }

    protected fun addLifecycleObserver(observer: LifecycleObserver?) {
        if (observer == null) {
            return
        }
        lifecycle.addObserver(observer)
    }
}