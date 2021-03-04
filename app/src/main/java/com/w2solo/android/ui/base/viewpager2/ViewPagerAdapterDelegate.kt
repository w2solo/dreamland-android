package com.w2solo.android.ui.base.viewpager2

import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

class ViewPagerAdapterDelegate {

    private val fragMap = HashMap<String, WeakReference<Fragment>>()

    fun getFragment(index: Int): Fragment? {
        return fragMap["page_$index"]?.get()
    }

    fun recordFragment(position: Int, frag: Fragment) {
        fragMap["page_$position"] = WeakReference(frag)
    }
}