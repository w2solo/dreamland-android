package com.w2solo.android.ui.topic

import com.w2solo.android.R
import com.w2solo.android.ui.topic.list.AbsTopicListFrag

class NodeTopicListFrag : AbsTopicListFrag() {

    private var currentNode: Long = -1

    companion object {
        val EXTRA_NODE = "extra_node"
    }

    override fun getLayout() = R.layout.simple_topic_list_fragment

    override fun getCurrentNodeId() = currentNode

    override fun initViews() {
        currentNode = arguments?.getLong(EXTRA_NODE, -1L)!!
        super.initViews()
    }
}