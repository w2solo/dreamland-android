package com.w2solo.android.ui.settings

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.w2solo.android.R
import com.w2solo.android.app.account.AccountManager
import com.w2solo.android.app.broadcast.AppBroadcast
import com.w2solo.android.ui.base.broadcast.BaseBroadcastReceiver
import com.w2solo.android.ui.base.fragment.BaseFragment
import com.w2solo.android.ui.commonfrag.CommonFragActivity

class SettingsFrag : BaseFragment() {

    override fun getLayout() = R.layout.settings_fragment

    override fun initViews() {
        fview<View>(R.id.settings_rules)?.setOnClickListener {
//            WebViewActivity.start(it.context,"https://www.w2solo.com/topics/1")
        }
        fview<View>(R.id.settings_group)?.setOnClickListener {
//            WebViewActivity.start(it.context,"https://www.w2solo.com/topics/1")
        }
        fview<View>(R.id.settings_account_layout)?.setOnClickListener {
            CommonFragActivity.start(it.context, R.string.title_login)
        }

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
        if (AccountManager.getInstance().isLogin) {
            val user = AccountManager.getInstance().loginUser
            name?.text = user?.name
            avatar?.setImageBitmap(null)
        } else {
            name?.setText(R.string.click_to_login)
            avatar?.setImageResource(R.drawable.ic_avatar_default)
        }
    }
}