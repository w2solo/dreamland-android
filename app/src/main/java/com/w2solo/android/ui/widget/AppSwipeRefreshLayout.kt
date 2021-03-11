package com.w2solo.android.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.w2solo.android.R

class AppSwipeRefreshLayout(context: Context, attrs: AttributeSet?) :
    SwipeRefreshLayout(context, attrs) {
    init {
        setColorSchemeColors(resources.getColor(R.color.theme_color_secondary))
    }
}