package com.w2solo.android.ui.topic.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w2solo.android.R
import com.w2solo.android.data.entity.Comment
import com.w2solo.android.ui.base.adapter.EmptyVH
import com.w2solo.android.utils.AppLog
import com.w2solo.markwon.recycler.ext.RecyclerAdapterDelegate

class TopicMarkdowAdapterDelegate(private val dataList: List<Comment>) : RecyclerAdapterDelegate {
    companion object {
        const val TAG = "TopicMarkdowAdapterDelegate"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        AppLog.d(TAG, "onCreateViewHolder---viewType=$viewType")
        if (viewType == R.layout.listitem_topic_detail_bottom_space) {
            val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            return EmptyVH(view)
        }
        return null
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        mdNodeCount: Int
    ): Boolean {
        if (holder is EmptyVH) {
            return true
        }
        AppLog.d(TAG, "onBindViewHolder---position=$position")
        return false
    }

    override fun getItemViewType(position: Int, mdNodeCount: Int): Int {
        AppLog.d(TAG, "getItemViewType---position=$position")
        if (position == getItemCount() + mdNodeCount - 1) {
            return R.layout.listitem_topic_detail_bottom_space
        }
        return -1
    }

    override fun isDelegateItem(holder: RecyclerView.ViewHolder): Boolean {
        AppLog.d(TAG, "isDelegateItem---holder=$holder")
        return false
    }

    override fun getItemCount() = dataList.size + 1 //+1 last empty space
}