package com.w2solo.android.ui.topic.detail

import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView
import com.w2solo.android.R
import com.w2solo.android.data.entity.Action
import com.w2solo.android.data.entity.Comment
import com.w2solo.android.ui.base.adapter.EasyHolder

class TopicCommentVH(itemView: View) : EasyHolder(itemView) {

    private val userAvatar = fview<ShapeableImageView>(R.id.comment_user_avatar)
    private val userName = fview<TextView>(R.id.comment_user_name)
    private val content = fview<TextView>(R.id.comment_content)

    fun bind(comment: Comment) {
        if (comment.user != null) {
            userName.text = "${comment.user!!.name}@${comment.user!!.login}"
            Glide.with(itemView.context)
                .load(comment.user!!.avatar_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_avatar_default)
                .into(userAvatar)
        } else {
            userAvatar.setImageResource(R.drawable.ic_avatar_default)
            userName.text = null
        }
        val clickListener = View.OnClickListener {
        }
        userAvatar.setOnClickListener(clickListener)
        userName.setOnClickListener(clickListener)

        if (comment.isDeleted) {
            content.setText(R.string.action_deleted)
            content.isEnabled = false
        } else {
            val action = comment.action
            if (!TextUtils.isEmpty(action)) {
                content.isEnabled = false
                content.setTypeface(null, Typeface.BOLD)
                val textResId =
                    when (action) {
                        Action.Excellent -> R.string.action_excellent
                        Action.Ban -> R.string.action_deleted
                        Action.Close -> R.string.action_close
                        Action.ReOpen -> R.string.action_reopen
                        else -> R.string.action_reopen
                    }
                content.setText(textResId)
            } else {
                content.isEnabled = true
                content.setTypeface(null, Typeface.NORMAL)
                content.text = comment.body
            }
        }
        itemView.setOnClickListener {
        }
    }
}