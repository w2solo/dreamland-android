package com.w2solo.android.ui.home

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.w2solo.android.R
import com.w2solo.android.ui.base.IScrollToTop
import com.w2solo.android.ui.base.activity.BaseToolbarActivity

class HomeActivity : BaseToolbarActivity(), HomeActContract.View {

    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: HomePagerAdapter
    private val presenter = HomeActPresenter(this)

    override fun getLayout() = R.layout.home_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager2 = fview(R.id.viewpager)
        adapter = HomePagerAdapter(this)
        viewPager2.adapter = adapter

        fview<View>(R.id.tab_topics).setOnClickListener {
            setTab(0, true)
        }
        fview<View>(R.id.tab_discover).setOnClickListener {
            setTab(1, true)
        }
        fview<View>(R.id.tab_me).setOnClickListener {
            setTab(2, true)
        }
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setTab(position, false)
            }
        })

        addLifecycleObserver(presenter)
        //check account token when app launch
        viewPager2.postDelayed({
            presenter.init()
        }, 1000)
    }

    private fun setTab(index: Int, byTab: Boolean) {
        fview<View>(R.id.tab_topics).isSelected = index == 0
        fview<View>(R.id.tab_discover).isSelected = index == 1
        fview<View>(R.id.tab_me).isSelected = index == 2
        if (viewPager2.currentItem == index) {
            //scrollToTop
            if (byTab) {
                val frag = adapter.getFragment(index)
                if (frag != null && frag is IScrollToTop) {
                    frag.scrollToTop()
                }
            }
            return
        }
        viewPager2.currentItem = index
    }
}