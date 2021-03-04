package com.w2solo.android.ui.settings

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.w2solo.android.R
import com.w2solo.android.app.account.AccountManager
import com.w2solo.android.app.broadcast.AppBroadcast
import com.w2solo.android.ui.base.broadcast.BaseBroadcastReceiver
import com.w2solo.android.ui.base.fragment.BaseFragment
import com.w2solo.android.ui.commonfrag.CommonFragActivity
import com.w2solo.android.ui.topic.detail.TopicDetailFragment
import com.w2solo.android.ui.user.userinfo.UserInfoFrag

class SettingsFrag : BaseFragment() {

    override fun getLayout() = R.layout.settings_fragment

    override fun initViews() {
        fview<View>(R.id.settings_rules)?.setOnClickListener {
            TopicDetailFragment.showTopic(it.context, 1L)
        }
        fview<View>(R.id.settings_group)?.setOnClickListener {
            TopicDetailFragment.showTopic(it.context, 40L)
        }
        fview<View>(R.id.settings_products)?.setOnClickListener {
            TopicDetailFragment.showTopic(it.context, 2L)
        }
        fview<View>(R.id.settings_account_layout)?.setOnClickListener {
            if (AccountManager.getInstance().isLogin) {
                UserInfoFrag.start(it.context!!, AccountManager.getInstance().loginUser!!)
                return@setOnClickListener
            }
            CommonFragActivity.start(it.context, R.string.title_login)
        }

//        fview<View>(R.id.settings_tuxiaochao)?.setOnClickListener {
//            val url = "https://support.qq.com/product/1221"
//            WebViewActivity.start(it.context, url, getString(R.string.app_feedback))
//        }
        registerBroadcastReceiver(object : BaseBroadcastReceiver() {
            override fun handleAction(intent: Intent) {
                if (AppBroadcast.ACCOUNT_CHANGE == intent.action) {
                    refreshAccount()
                }
            }
        }, AppBroadcast.ACCOUNT_CHANGE)

        refreshAccount()
    }

    private fun refreshAccount() {
        val avatar = fview<ImageView>(R.id.settings_account_avatar)
        val name = fview<TextView>(R.id.settings_account_name)

        val toolbar = fview<Toolbar>(R.id.settings_toolbar)
        if (AccountManager.getInstance().isLogin) {
            toolbar?.inflateMenu(R.menu.menu_settings)
            toolbar?.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_logout) {
                    AccountManager.getInstance().logout()
                }
                true
            }
            val user = AccountManager.getInstance().loginUser
            name?.text = user?.name
            avatar?.setImageBitmap(null)
            Glide.with(context!!)
                .load(user?.avatar_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_avatar_default)
                .into(avatar!!)
        } else {
            toolbar?.menu?.clear()
            name?.setText(R.string.click_to_login)
            avatar?.setImageResource(R.drawable.ic_avatar_default)
        }
    }
}