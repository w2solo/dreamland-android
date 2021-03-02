package com.w2solo.android.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.w2solo.android.ui.topic.list.TopicListFrag

class HomePagerAdapter(act: FragmentActivity) : FragmentStateAdapter(act) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return TopicListFrag()
        }
        return TopicListFrag()
    }
}