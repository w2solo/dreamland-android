package com.w2solo.android.ui.discover

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.w2solo.android.app.AppConfigs
import com.w2solo.android.ui.base.fragment.BaseFragment
import com.w2solo.android.ui.settings.SettingsFrag
import com.w2solo.android.ui.topic.NodeTopicListFrag
import java.lang.ref.WeakReference

class DiscoverPagerAdapter(frag: Fragment) : FragmentStateAdapter(frag) {

    private val fragMap = HashMap<String, WeakReference<BaseFragment>>()

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        var frag = getFragment(position)
        if (frag == null) {
            frag =
                when (position) {
                    0 -> {
                        val arg = Bundle()
                        arg.putLong(NodeTopicListFrag.EXTRA_NODE, AppConfigs.NODE_NEW_USERS)
                        val frag = NodeTopicListFrag()
                        frag.arguments = arg
                        frag
                    }
                    1 -> {
                        val arg = Bundle()
                        arg.putLong(NodeTopicListFrag.EXTRA_NODE, AppConfigs.NODE_PRODUCTS)
                        val frag = NodeTopicListFrag()
                        frag.arguments = arg
                        frag
                    }
                    2 -> SettingsFrag()
                    else -> NodeTopicListFrag()
                }
            fragMap["page_$position"] = WeakReference(frag)
        }
        return frag
    }

    fun getFragment(index: Int): BaseFragment? {
        return fragMap["page_$index"]?.get()
    }
}