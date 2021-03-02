package com.w2solo.android.ui.home

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.w2solo.android.R
import com.w2solo.android.ui.base.activity.BaseToolbarActivity

class HomeActivity : BaseToolbarActivity() {

    private lateinit var viewPager2: ViewPager2
    override fun getLayout() = R.layout.home_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager2 = fview(R.id.viewpager)
        viewPager2.adapter = HomePagerAdapter(this)

        fview<View>(R.id.tab_topics).setOnClickListener {
            setTab(0)
        }
        fview<View>(R.id.tab_discover).setOnClickListener {
            setTab(1)
        }
        fview<View>(R.id.tab_me).setOnClickListener {
            setTab(2)
        }
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setTab(position)
            }
        })
    }

    private fun setTab(index: Int) {
        fview<View>(R.id.tab_topics).isSelected = index == 0
        fview<View>(R.id.tab_discover).isSelected = index == 1
        fview<View>(R.id.tab_me).isSelected = index == 2
        if (viewPager2.currentItem == index) {
            return
        }
        viewPager2.currentItem = index
    }
}