package com.w2solo.markwon.recycler.ext

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface RecyclerAdapterDelegate {

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder?

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, mdNodeCount: Int): Boolean

    fun getItemViewType(position: Int, mdNodeCount: Int): Int

    fun isDelegateItem(holder: RecyclerView.ViewHolder): Boolean

    fun getItemCount(): Int

    /**
     * Add a header before markdown list
     */
    fun getHeaderCount(): Int
}