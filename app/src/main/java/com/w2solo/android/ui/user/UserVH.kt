package com.w2solo.android.ui.user

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView
import com.w2solo.android.R
import com.w2solo.android.data.entity.User
import com.w2solo.android.ui.base.adapter.EasyHolder
import com.w2solo.android.ui.user.userinfo.UserInfoFrag

class UserVH(itemView: View) : EasyHolder(itemView) {

    private val userAvatar = fview<ShapeableImageView>(R.id.user_avatar)
    private val userName = fview<TextView>(R.id.user_name)

    fun bind(user: User) {
        if (TextUtils.isEmpty(user.avatar_url)) {
            userAvatar.setImageResource(R.drawable.ic_avatar_default)
        } else {
            Glide.with(itemView.context)
                .load(user.avatar_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_avatar_default)
                .into(userAvatar)
        }
        userName.text = "${user.name}@${user.login}"
        itemView.setOnClickListener {
            UserInfoFrag.start(it.context, user)
        }
    }
}