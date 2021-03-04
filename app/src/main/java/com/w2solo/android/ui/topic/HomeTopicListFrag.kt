package com.w2solo.android.ui.topic

import com.w2solo.android.R
import com.w2solo.android.ui.topic.list.AbsTopicListFrag

class HomeTopicListFrag : AbsTopicListFrag() {

    private var currentNodeId = -1L

    override fun getCurrentNodeId() = currentNodeId

    override fun getLayout() = R.layout.home_topic_list_fragment

    override fun initViews() {
        super.initViews()
//        val button = fview<Button>(R.id.home_current_node)!!
//        var nodeName = KV.getSysStr(KVKeys.KEY_HOME_CURRENT_NODE_NAME, null)
//        val nodeID = KV.getSysLong(KVKeys.KEY_HOME_CURRENT_NODE_ID, -1L)
//        if (nodeID > 0 && !TextUtils.isEmpty(nodeName)) {
//            currentNodeId = nodeID
//        } else {
//            nodeName = getString(R.string.all_node)
//        }
//        button.text = nodeName
//        button.setOnClickListener {
//        }
    }
}