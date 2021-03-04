package com.w2solo.android.ui.topic.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w2solo.android.R
import com.w2solo.android.data.entity.Comment
import com.w2solo.android.ui.base.adapter.EmptyVH
import com.w2solo.android.utils.AppLog
import com.w2solo.markwon.recycler.ext.RecyclerAdapterDelegate

class TopicMarkdownAdapterDelegate(
    private val dataList: List<Comment>,
    private val onItemClickListener: OnItemClickListener? = null
) : RecyclerAdapterDelegate {
    companion object {
        const val TAG = "TopicMarkdownAdapterDelegate"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        AppLog.d(TAG, "onCreateViewHolder---viewType=$viewType")
        if (viewType == R.layout.listitem_topic_detail_bottom_space
            || viewType == R.layout.listitem_topic_comment_header
        ) {
            val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            return EmptyVH(view)
        } else if (viewType == R.layout.listitem_topic_comment) {
            val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            return TopicCommentVH(view)
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
        } else if (holder is TopicCommentVH) {
            val dataIndex = position - mdNodeCount - 1
            holder.bind(dataList[dataIndex])
            holder.itemView.setTag(R.id.tag_view_data, dataIndex)
            holder.itemView.setOnClickListener {
                val tag = it.getTag(R.id.tag_view_data)
                if (tag != null && tag is Int) {
                    onItemClickListener?.onItemClick(dataList[tag])
                }
            }
            return true
        }
        AppLog.d(TAG, "onBindViewHolder---position=$position")
        return false
    }

    override fun getItemViewType(position: Int, mdNodeCount: Int): Int {
        AppLog.d(TAG, "getItemViewType---position=$position ")
        return when {
            position == mdNodeCount -> R.layout.listitem_topic_comment_header
            position == mdNodeCount + getItemCount() - 1 -> R.layout.listitem_topic_detail_bottom_space
            position >= mdNodeCount + 1 -> R.layout.listitem_topic_comment
            else -> -1
        }
    }

    override fun isDelegateItem(holder: RecyclerView.ViewHolder): Boolean {
        AppLog.d(TAG, "isDelegateItem---holder=$holder")
        val viewType = holder.itemViewType
        return viewType == R.layout.listitem_topic_comment_header
                || viewType == R.layout.listitem_topic_detail_bottom_space
                || viewType == R.layout.listitem_topic_comment
    }

    override fun getItemCount() = dataList.size + 1 + 1 //+1 topic header,+1 last empty space

    interface OnItemClickListener {
        fun onItemClick(comment: Comment)
    }
}