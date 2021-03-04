package com.w2solo.android.ui.user.userreplies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w2solo.android.R
import com.w2solo.android.data.entity.Comment
import com.w2solo.android.ui.topic.detail.TopicCommentVH

class CommentListAdapter(
    private val dataList: List<Comment>,
    private var callback: Callback? = null
) :
    RecyclerView.Adapter<TopicCommentVH>() {

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicCommentVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_topic_comment, parent, false)
        return TopicCommentVH(view)
    }

    override fun onBindViewHolder(holder: TopicCommentVH, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            callback?.onCommentClicked(dataList[position])
        }
    }

    interface Callback {
        fun onCommentClicked(comment: Comment)
    }
}