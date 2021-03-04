package com.w2solo.android.ui.discover

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.w2solo.android.R
import com.w2solo.android.ui.base.fragment.BaseFragment

class DiscoverFrag : BaseFragment() {

    override fun getLayout() = R.layout.discover_home_fragment

    override fun initViews() {
        val viewPager = fview<ViewPager2>(R.id.viewpager)
        val tabLayout = fview<TabLayout>(R.id.tab_layout)
        viewPager!!.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
        tabLayout!!.addTab(
            tabLayout.newTab().setText(R.string.discover_tab_new_hackers)
//                .setCustomView(R.layout.common_tab)
        )
        tabLayout.addTab(
            tabLayout.newTab().setText(R.string.discover_tab_products)
//                .setCustomView(R.layout.common_tab)
        )
        tabLayout.addTab(
            tabLayout.newTab().setText(R.string.discover_tab_top_hackers)
//                .setCustomView(R.layout.common_tab)
        )
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }
        })
        viewPager.adapter = DiscoverPagerAdapter(this)
    }
}