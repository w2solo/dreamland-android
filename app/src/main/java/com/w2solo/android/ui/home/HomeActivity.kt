package com.w2solo.android.ui.home

import android.os.Bundle
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
    }
}