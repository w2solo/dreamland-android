package com.w2solo.android.ui.user.userinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.w2solo.android.ui.base.viewpager2.ViewPagerAdapterDelegate
import com.w2solo.android.ui.topic.UserTopicListFrag

class UserInfoPagerAdapter(private val userLogin: String, frag: Fragment) :

    FragmentStateAdapter(frag) {

    private val delegate = ViewPagerAdapterDelegate()

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        var frag = getFragment(position)
        if (frag == null) {
            val args = Bundle()
            args.putString(UserTopicListFrag.EXTRA_LOGIN, userLogin)
            frag = UserTopicListFrag()
            frag.arguments = args
            delegate.recordFragment(position, frag)
        }
        return frag
    }

    fun getFragment(position: Int): Fragment? {
        return delegate.getFragment(position)
    }
}