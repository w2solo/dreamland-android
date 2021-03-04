package com.w2solo.android.ui.topic.detail

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView
import com.w2solo.android.R
import com.w2solo.android.data.entity.Topic
import com.w2solo.android.ui.base.adapter.EasyHolder
import com.w2solo.android.ui.topic.NodeTopicListFrag
import com.w2solo.android.ui.user.userinfo.UserInfoFrag

class TopicDetailVH(itemView: View) : EasyHolder(itemView) {

    private val title = fview<TextView>(R.id.topic_title)

    private val userLayout = fview<View>(R.id.topic_user_layout)
    private val userAvatar = fview<ShapeableImageView>(R.id.topic_user_avatar)
    private val userName = fview<TextView>(R.id.topic_user_name)
    private val time = fview<TextView>(R.id.topic_time)
    private val topicNodeLayout = fview<TextView>(R.id.topic_node_layout)
    private val topicNode = fview<TextView>(R.id.topic_node)

    fun bind(topic: Topic) {
        title.text = topic.title
        val user = topic.user
        if (user != null) {
            userLayout.setOnClickListener {
                UserInfoFrag.start(it.context, user!!)
            }
            if (!TextUtils.isEmpty(user.avatar_url)) {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_avatar_default)
                    .into(userAvatar)
            } else {
                userAvatar.setImageResource(R.drawable.ic_avatar_default)
            }
            userName.text = "${user.name}@${user.login}"
        }
        time.text = topic.createdTime
        if (!TextUtils.isEmpty(topic.nodeName)) {
            topicNodeLayout.visibility = View.VISIBLE
            topicNode.text = topic.nodeName
            topicNodeLayout.setOnClickListener {
                NodeTopicListFrag.start(it.context!!, topic.nodeName, topic.nodeId)
            }
        } else {
            topicNodeLayout.visibility = View.INVISIBLE
        }
    }
}