package com.w2solo.android.ui.topic.list

import android.view.View
import com.w2solo.android.data.entity.Topic
import com.w2solo.android.ui.base.adapter.EasyHolder

class TopicVH(itemView: View) : EasyHolder(itemView) {

    fun bind(topic: Topic?) {
        if (topic == null) {
            return
        }
    }
}