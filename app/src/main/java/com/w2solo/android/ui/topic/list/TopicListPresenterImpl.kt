package com.w2solo.android.ui.topic.list

import com.w2solo.android.http.Requester
import com.w2solo.android.http.result.TopicListBean
import com.w2solo.android.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TopicListPresenterImpl(view: TopicListContract.View) : TopicListContract.Presenter,

    BasePresenter<TopicListContract.View>(view) {

    private var isLoading = false
    private val pageSize = 20
    private var pageIndex = 0

    override fun loadListByNode(isRefresh: Boolean, nodeId: Long) {
        if (isLoading) {
            return
        }
        isLoading = true
        if (isRefresh) {
            pageIndex = 0
        }
        val offset = pageIndex * pageSize

        val observable =
            if (nodeId > 0) {
                Requester.apiService().getTopicListByNode(nodeId, offset, pageSize)
            } else {
                Requester.apiService().getTopicList(offset, pageSize)
            }
        val disposable = observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading = false
                pageIndex++
                view?.onGetList((it as TopicListBean).list, isRefresh)
            }) {
                isLoading = false
                view?.onGetList(null, isRefresh)
            }
        runDisposable(disposable)
    }

    override fun loadListByUser(isRefresh: Boolean, userLogin: String) {
        if (isLoading) {
            return
        }
        isLoading = true
        val disposable = Requester.apiService().getTopicListByUser(userLogin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading = false
                view?.onGetList((it as TopicListBean).list, isRefresh)
            }) {
                isLoading = false
                view?.onGetList(null, isRefresh)
            }
        runDisposable(disposable)
    }
}