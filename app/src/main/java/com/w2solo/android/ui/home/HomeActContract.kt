package com.w2solo.android.ui.home

import com.w2solo.android.mvp.IPresenter
import com.w2solo.android.mvp.IView

interface HomeActContract {

    interface View : IView

    interface Presenter : IPresenter<View> {
        fun init()
    }
}