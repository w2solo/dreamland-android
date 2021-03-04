package com.w2solo.android.ui.discover

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.w2solo.android.app.AppConfigs
import com.w2solo.android.ui.base.viewpager2.ViewPagerAdapterDelegate
import com.w2solo.android.ui.user.topusers.TopUsersListFrag
import com.w2solo.android.ui.topic.NodeTopicListFrag

class DiscoverPagerAdapter(frag: Fragment) : FragmentStateAdapter(frag) {

    private val delegate = ViewPagerAdapterDelegate()

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        var frag = getFragment(position)
        if (frag == null) {
            frag =
                when (position) {
                    0 -> {
                        val arg = Bundle()
                        arg.putLong(NodeTopicListFrag.EXTRA_NODE, AppConfigs.NODE_PRODUCTS)
                        val frag = NodeTopicListFrag()
                        frag.arguments = arg
                        frag
                    }
                    1 -> {
                        val arg = Bundle()
                        arg.putLong(NodeTopicListFrag.EXTRA_NODE, AppConfigs.NODE_NEW_USERS)
                        val frag = NodeTopicListFrag()
                        frag.arguments = arg
                        frag
                    }
                    else -> TopUsersListFrag()
                }
            delegate.recordFragment(position, frag)
        }
        return frag
    }

    fun getFragment(index: Int) = delegate.getFragment(index)
}