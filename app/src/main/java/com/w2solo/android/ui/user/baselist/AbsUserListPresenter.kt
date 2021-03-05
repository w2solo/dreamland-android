package com.w2solo.android.ui.user.baselist

import com.w2solo.android.mvp.BasePresenter

abstract class AbsUserListPresenter(view: AbsUsersListContract.View) :
    BasePresenter<AbsUsersListContract.View>(view), AbsUsersListContract.Presenter {
}