package com.w2solo.android.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.w2solo.android.ui.base.fragment.BaseFragment
import com.w2solo.android.ui.discover.DiscoverFrag
import com.w2solo.android.ui.settings.SettingsFrag
import com.w2solo.android.ui.topic.HomeTopicListFrag
import java.lang.ref.WeakReference

class HomePagerAdapter(act: FragmentActivity) : FragmentStateAdapter(act) {

    private val fragMap = HashMap<String, WeakReference<BaseFragment>>()

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        var frag = getFragment(position)
        if (frag == null) {
            frag =
                when (position) {
                    0 -> HomeTopicListFrag()
                    1 -> DiscoverFrag()
                    2 -> SettingsFrag()
                    else -> HomeTopicListFrag()
                }
            fragMap["page_$position"] = WeakReference(frag)
        }
        return frag
    }

    fun getFragment(index: Int): BaseFragment? {
        return fragMap["page_$index"]?.get()
    }
}