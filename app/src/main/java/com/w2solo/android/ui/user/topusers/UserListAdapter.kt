package com.w2solo.android.ui.user.topusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w2solo.android.R
import com.w2solo.android.data.entity.User

class UserListAdapter(private val dataList: List<User>) : RecyclerView.Adapter<UserVH>() {

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.listitem_user, parent, false)
        return UserVH(view)
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.bind(dataList[position])
    }
}