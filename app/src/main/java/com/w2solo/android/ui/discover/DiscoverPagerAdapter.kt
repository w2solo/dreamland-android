package com.w2solo.android.ui.discover

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.w2solo.android.app.AppConfigs
import com.w2solo.android.ui.base.viewpager2.ViewPagerAdapterDelegate
import com.w2solo.android.ui.topic.NodeTopicListFrag
import com.w2solo.android.ui.user.topusers.TopUsersListFrag

class DiscoverPagerAdapter(frag: Fragment) : FragmentStateAdapter(frag) {

    private val delegate = ViewPagerAdapterDelegate()

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        var frag = getFragment(position)
        if (frag == null) {
            frag =
                when (position) {
                    0, 1 -> {
                        val arg = Bundle()
                        arg.putLong(
                            NodeTopicListFrag.EXTRA_NODE_ID,
                            if (position == 0) AppConfigs.NODE_PRODUCTS else AppConfigs.NODE_NEW_USERS
                        )
                        val newFrag = NodeTopicListFrag()
                        newFrag.arguments = arg
                        newFrag
                    }
                    else -> TopUsersListFrag()
                }
            delegate.recordFragment(position, frag)
        }
        return frag
    }

    fun getFragment(index: Int) = delegate.getFragment(index)
}