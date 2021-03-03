package com.w2solo.android.ui.topic.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w2solo.android.R
import com.w2solo.android.data.entity.Comment
import com.w2solo.android.data.entity.Topic
import com.w2solo.android.ui.base.adapter.EasyHolder

class TopicDetailAdapter(private val topic: Topic, private val dataList: List<Comment>) :
    RecyclerView.Adapter<EasyHolder>() {

    override fun getItemCount() = dataList.size + 2 //+1 top-header & bottom-empty-space

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return R.layout.listitem_topic_detail_header
        if (position == itemCount - 1) return R.layout.listitem_topic_detail_bottom_space
        return R.layout.listitem_topic_comment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EasyHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        if (viewType == R.layout.listitem_topic_detail_header) {
            return TopicDetailVH(view)
        } else if (viewType == R.layout.listitem_topic_detail_bottom_space) {
            return object : EasyHolder(view) {}
        }
        return TopicCommentVH(view)
    }

    override fun onBindViewHolder(holder: EasyHolder, position: Int) {
        if (holder is TopicDetailVH) {
            holder.bind(topic)
        } else if (holder is TopicCommentVH) {
            holder.bind(dataList[position - 1])
        }
    }
}