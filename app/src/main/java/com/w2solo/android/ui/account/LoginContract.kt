package com.w2solo.android.ui.account

import com.w2solo.android.data.entity.User
import com.w2solo.android.mvp.IPresenter
import com.w2solo.android.mvp.IView

interface LoginContract {

    interface View : IView {
        fun onLogin(user: User?)
    }

    interface Presenter : IPresenter<View> {
        fun startLogin(account: String, pwd: String)
    }
}