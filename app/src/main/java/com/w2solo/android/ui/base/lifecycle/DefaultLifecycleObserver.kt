package com.w2solo.android.ui.base.lifecycle

import androidx.lifecycle.LifecycleOwner

abstract class DefaultLifecycleObserver: ILifecycleObserver {
    override fun onCreate(owner: LifecycleOwner?) {}

    override fun onStart(owner: LifecycleOwner?) {}

    override fun onResume(owner: LifecycleOwner?) {}

    override fun onPause(owner: LifecycleOwner?) {}

    override fun onStop(owner: LifecycleOwner?) {}

    override fun onDestroy(owner: LifecycleOwner?) {}
}