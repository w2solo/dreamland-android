package com.w2solo.android.ui.topic.list

import com.w2solo.android.data.entity.Topic
import com.w2solo.android.mvp.BasePresenter

abstract class AbsTopicListPresenterImpl(view: TopicListContract.View) :
    TopicListContract.Presenter,
    BasePresenter<TopicListContract.View>(view) {

    private var isLoading = false
    private val pageSize = 20
    private var pageIndex = 0
    private var hasMore = true

    override fun loadList(isRefresh: Boolean) {
        if (isLoading) {
            return
        }
        if (isRefresh) {
            pageIndex = 0
        } else {
            if (!hasMore) {
                return
            }
        }
        isLoading = true
        doLoad(isRefresh, pageIndex * pageSize, pageSize)
    }

    protected fun onLoadFinished(newList: List<Topic>?) {
        isLoading = false
        if (newList != null) {
            hasMore = newList.size >= pageSize
            pageIndex++
        }
    }

    abstract fun doLoad(isRefresh: Boolean, offset: Int, limit: Int)
}