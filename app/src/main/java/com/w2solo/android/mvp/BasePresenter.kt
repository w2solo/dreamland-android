package com.w2solo.android.mvp

import androidx.lifecycle.LifecycleOwner
import com.w2solo.android.ui.base.lifecycle.ILifecycleObserver

abstract class BasePresenter<V : IView>(var view: V? = null) : IPresenter<V>, ILifecycleObserver {
    override fun onCreate(owner: LifecycleOwner?) {
    }

    override fun onStart(owner: LifecycleOwner?) {
    }

    override fun onResume(owner: LifecycleOwner?) {
    }

    override fun onPause(owner: LifecycleOwner?) {
    }

    override fun onStop(owner: LifecycleOwner?) {
    }

    override fun onDestroy(owner: LifecycleOwner?) {
    }
}