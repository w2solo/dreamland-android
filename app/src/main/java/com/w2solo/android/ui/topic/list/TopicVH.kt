package com.w2solo.android.ui.topic.list

import android.view.View
import android.widget.TextView
import com.w2solo.android.R
import com.w2solo.android.data.entity.Topic
import com.w2solo.android.ui.base.adapter.EasyHolder

class TopicVH(itemView: View) : EasyHolder(itemView) {

    private val title = fview<TextView>(R.id.topic_title)
    private val info = fview<TextView>(R.id.topic_info)

    fun bind(topic: Topic?) {
        if (topic == null) {
            return
        }
        title.text = topic.title

        val sb = StringBuilder()
        val user = topic.user
        if (user != null) {
            sb.append("@${user.name}")
                .append("·")
        }
        if (topic.repliesCount > 0) {
            sb.append("阅读 ")
                .append("${topic.repliesCount}")
        }
        info.text = sb
    }
}