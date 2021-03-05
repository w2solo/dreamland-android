package com.w2solo.android.ui.user.topusers

import com.w2solo.android.ui.user.baselist.AbsUsersListContract
import com.w2solo.android.ui.user.baselist.AbsUsersListFrag

class TopUsersListFrag : AbsUsersListFrag() {

    override fun canLoadMore() = false

    override fun getPresenter(view: AbsUsersListContract.View) = TopUserListPresenterImpl(this)
}