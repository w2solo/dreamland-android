package com.w2solo.android.ui.topic.nodetopics

import com.w2solo.android.http.Requester
import com.w2solo.android.ui.topic.list.AbsTopicListPresenterImpl
import com.w2solo.android.ui.topic.list.TopicListContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NodeTopicsListPresenterImpl(private val nodeId: Long, view: TopicListContract.View) :
    AbsTopicListPresenterImpl(view) {

    override fun doLoad(isRefresh: Boolean, offset: Int, limit: Int) {
        val observable =
            if (nodeId > 0) {
                Requester.apiService().getTopicListByNode(nodeId, offset, limit)
            } else {
                Requester.apiService().getTopicList(offset, limit)
            }
        val disposable = observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onLoadFinished(it.list)
                view?.onGetList(it.list, isRefresh)
            }) {
                onLoadFinished(null)
                view?.onGetList(null, isRefresh)
            }
        runDisposable(disposable)
    }
}