package com.w2solo.android.ui.topic.detail

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.w2solo.android.R
import com.w2solo.android.data.entity.Topic
import com.w2solo.android.ui.base.adapter.EasyHolder
import com.w2solo.android.ui.topic.markdown.MDParser

class TopicDetailVH(itemView: View) : EasyHolder(itemView) {
    private val title = fview<TextView>(R.id.topic_title)
    private val body = fview<TextView>(R.id.topic_body)
    fun bind(topic: Topic) {
        title.text = topic.title
        if (TextUtils.isEmpty(topic.bodyHtml)) {
            body.visibility = View.GONE
        } else {
            body.visibility = View.VISIBLE
            body.text = MDParser.parseMarkDown(itemView.context, topic.bodyHtml!!)
        }
    }
}