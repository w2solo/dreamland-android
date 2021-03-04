package com.w2solo.android.ui.topic

import com.w2solo.android.R
import com.w2solo.android.ui.topic.list.AbsTopicListFrag

class UserTopicListFrag : AbsTopicListFrag() {

    private var currentLogin: String? = null

    companion object {
        val EXTRA_LOGIN = "extra_login"
    }

    override fun getLayout() = R.layout.simple_topic_list_fragment

    override fun getCurrentNodeId() = -1L

    override fun getUserLogin() = currentLogin

    override fun canLoadMore() = false

    override fun initViews() {
        currentLogin = arguments?.getString(EXTRA_LOGIN)
        super.initViews()
    }
}