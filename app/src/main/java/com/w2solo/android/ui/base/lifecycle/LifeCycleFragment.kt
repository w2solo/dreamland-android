package com.w2solo.android.ui.base.lifecycle

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

abstract class LifeCycleFragment : Fragment(), LifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        addLifecycleObserver(LoggerLifecycleObserver())
        super.onCreate(savedInstanceState)
    }

    protected fun addLifecycleObserver(observer: LifecycleObserver?) {
        lifecycle.addObserver(observer!!)
    }
}