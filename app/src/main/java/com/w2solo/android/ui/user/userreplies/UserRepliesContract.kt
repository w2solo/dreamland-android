package com.w2solo.android.ui.user.userreplies

import com.w2solo.android.data.entity.Comment
import com.w2solo.android.mvp.IPresenter
import com.w2solo.android.mvp.IView

interface UserRepliesContract {

    interface View : IView {
        fun onGetList(newList: List<Comment>?, isRefresh: Boolean)
    }

    interface Presenter : IPresenter<View> {
        fun loadList(isRefresh: Boolean, userLogin: String)
    }
}