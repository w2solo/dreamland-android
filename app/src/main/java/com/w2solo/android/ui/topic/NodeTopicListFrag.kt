package com.w2solo.android.ui.topic

import android.content.Context
import android.os.Bundle
import com.w2solo.android.R
import com.w2solo.android.ui.commonfrag.CommonFragActivity
import com.w2solo.android.ui.topic.list.AbsTopicListFrag

class NodeTopicListFrag : AbsTopicListFrag() {

    private var currentNode: Long = -1

    companion object {

        val EXTRA_NODE_ID = "extra_node"

        fun start(context: Context, nodeName: String?, nodeID: Long) {
            val args = Bundle()
            args.putLong(EXTRA_NODE_ID, nodeID)
            args.putString(CommonFragActivity.Companion.KEY_TITLE_TEXT, nodeName)
            CommonFragActivity.start(context, R.string.title_node_details, args)
        }
    }

    override fun getLayout() = R.layout.simple_topic_list_fragment

    override fun getCurrentNodeId() = currentNode

    override fun initViews() {
        currentNode = arguments?.getLong(EXTRA_NODE_ID, -1L)!!
        super.initViews()
    }
}