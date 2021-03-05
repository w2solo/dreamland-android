package com.w2solo.android.ui.user.follow

import android.os.Bundle
import com.w2solo.android.ui.user.baselist.AbsUsersListContract
import com.w2solo.android.ui.user.baselist.AbsUsersListFrag

class UserFollowListFrag : AbsUsersListFrag() {

    companion object {
        const val EXTRA_USER_LOGIN = "extra_login"
    }

    private var userLogin: String = ""

    override fun canLoadMore() = true

    override fun getPresenter(view: AbsUsersListContract.View) =
        UserFollowListPresenterImpl(userLogin, this)

    override fun initViews() {
        userLogin = arguments?.getString(EXTRA_USER_LOGIN, "") ?: ""
        super.initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EXTRA_USER_LOGIN, userLogin)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        userLogin = savedInstanceState?.getString(EXTRA_USER_LOGIN, "") ?: ""
    }
}