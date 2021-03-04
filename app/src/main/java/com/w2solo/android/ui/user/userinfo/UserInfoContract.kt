package com.w2solo.android.ui.user.userinfo

import com.w2solo.android.data.entity.User
import com.w2solo.android.data.entity.UserMeta
import com.w2solo.android.mvp.IPresenter
import com.w2solo.android.mvp.IView

interface UserInfoContract {

    interface View : IView {

        fun onGetInfo(user: User?, meta: UserMeta?)

        fun onFollowOrNot(isSuccess: Boolean, newFollowStatus: Boolean)
    }

    interface Presenter : IPresenter<View> {
        fun loadUserInfo(userLogin: String)

        fun followOrNot(userLogin: String, followOrNot: Boolean)
    }
}