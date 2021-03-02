package com.w2solo.android.ui.topic.list

import com.w2solo.android.http.Requester
import com.w2solo.android.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TopicListPresenterImpl(view: TopicListContract.View) : TopicListContract.Presenter,
    BasePresenter<TopicListContract.View>(view) {

    private val pageSize = 20
    private var pageIndex = 0

    override fun loadList(isRefresh: Boolean) {
        if (isRefresh) {
            pageIndex = 0
        }
        val offset = pageIndex * pageSize
        val disposable = Requester.apiService().getTopicList(offset, pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                view?.onGetList(data.list, isRefresh)
            }
        runDisposable(disposable)
    }
}