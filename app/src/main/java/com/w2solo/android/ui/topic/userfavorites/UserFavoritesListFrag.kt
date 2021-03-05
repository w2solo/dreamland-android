package com.w2solo.android.ui.topic.userfavorites

import android.os.Bundle
import com.w2solo.android.R
import com.w2solo.android.ui.topic.list.AbsTopicListFrag
import com.w2solo.android.ui.topic.list.TopicListContract

class UserFavoritesListFrag : AbsTopicListFrag() {

    private var userLogin: String = ""

    companion object {
        val EXTRA_LOGIN = "extra_login"
    }

    override fun getLayout() = R.layout.simple_topic_list_fragment

    override fun canLoadMore() = true

    override fun getPresenter(view: TopicListContract.View) =
        UserFavoritesListPresenterImpl(userLogin, view)

    override fun initViews() {
        userLogin = arguments?.getString(EXTRA_LOGIN) ?: ""
        super.initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EXTRA_LOGIN, userLogin)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        userLogin = savedInstanceState?.getString(EXTRA_LOGIN) ?: ""
    }
}