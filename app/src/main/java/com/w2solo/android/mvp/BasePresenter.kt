package com.w2solo.android.mvp

import androidx.lifecycle.LifecycleOwner
import com.w2solo.android.ui.base.lifecycle.ILifecycleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : IView>(var view: V?) : IPresenter<V>, ILifecycleObserver {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

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
        unSubscription()
    }

    fun runDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    open fun unSubscription() {
        compositeDisposable.clear()
    }
}