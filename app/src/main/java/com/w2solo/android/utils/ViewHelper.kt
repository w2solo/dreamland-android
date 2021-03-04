package com.w2solo.android.utils

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.w2solo.android.R

object ViewHelper {
    fun buildRecyclerViewDivider(context: Context): DividerItemDecoration {
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider)!!)
        return divider
    }
}