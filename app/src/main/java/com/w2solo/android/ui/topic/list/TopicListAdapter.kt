package com.w2solo.android.ui.topic.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w2solo.android.data.entity.Topic

class TopicListAdapter(private val dataList: List<Topic>) : RecyclerView.Adapter<TopicVH>() {

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TopicVH(LayoutInflater.from(parent.context).inflate(viewType, null))

    override fun onBindViewHolder(holder: TopicVH, position: Int) {
        holder.bind(dataList[position])
    }
}