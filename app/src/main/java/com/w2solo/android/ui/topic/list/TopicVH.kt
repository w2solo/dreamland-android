package com.w2solo.android.ui.topic.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.w2solo.android.R
import com.w2solo.android.data.entity.Topic
import com.w2solo.android.ui.base.adapter.EasyHolder
import com.w2solo.android.ui.topic.detail.TopicDetailFragment

class TopicVH(itemView: View) : EasyHolder(itemView) {

    private val avatar = fview<ImageView>(R.id.topic_user_avatar)
    private val title = fview<TextView>(R.id.topic_title)
    private val info = fview<TextView>(R.id.topic_info)

    fun bind(topic: Topic?) {
        if (topic == null) {
            return
        }
        Glide.with(itemView.context)
            .load(topic.user?.avatar_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_avatar_default)
            .into(avatar)
        title.text = topic.title

        val sb = StringBuilder()
        val user = topic.user
        if (user != null) {
            sb.append("@${user.name}")
        }
        if (topic.repliesCount > 0) {
            sb.append("·")
                .append(
                    itemView.context.getString(
                        R.string.topic_replies_count,
                        topic.repliesCount
                    )
                )
        }
        info.text = sb
        itemView.setOnClickListener {
            TopicDetailFragment.showTopic(it.context, topic)
        }
    }
}