package com.w2solo.android.ui.base.adapter

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

abstract class EasyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected fun <T : View> fview(
        @IdRes id: Int, onClickListener: View.OnClickListener? = null
    ): T {
        val view: T = itemView.findViewById(id)
        if (onClickListener != null) {
            view.setOnClickListener(onClickListener)
        }
        return view
    }

}