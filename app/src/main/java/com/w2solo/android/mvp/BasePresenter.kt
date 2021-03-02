package com.w2solo.android.mvp

abstract class BasePresenter<V : IView>(var view: V? = null) : IPresenter<V> {
}