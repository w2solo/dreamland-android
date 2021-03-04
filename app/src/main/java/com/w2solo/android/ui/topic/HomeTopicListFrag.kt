package com.w2solo.android.ui.topic

import com.w2solo.android.R
import com.w2solo.android.ui.topic.list.AbsTopicListFrag

class HomeTopicListFrag : AbsTopicListFrag() {

    override fun getCurrentNodeId() = -1L

    override fun getLayout() = R.layout.home_topic_list_fragment
}