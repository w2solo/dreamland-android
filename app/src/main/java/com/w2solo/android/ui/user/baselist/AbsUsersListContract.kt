package com.w2solo.android.ui.user.baselist

import com.w2solo.android.data.entity.User
import com.w2solo.android.mvp.IPresenter
import com.w2solo.android.mvp.IView

interface AbsUsersListContract {

    interface View : IView {
        fun onGetList(newList: List<User>?, isRefresh: Boolean)
    }

    interface Presenter : IPresenter<View> {
        fun loadList(isRefresh: Boolean)
    }
}