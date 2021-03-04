package com.w2solo.android.ui.topic.detail

import com.w2solo.android.http.Requester
import com.w2solo.android.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TopicDetailPresenterImpl(view: TopicDetailContract.View) :
    BasePresenter<TopicDetailContract.View>(view), TopicDetailContract.Presenter {

    private var pageIndex = 0
    private val pageSize = 20

    override fun loadTopic(topicId: Long) {
        //load from api
        val disposable = Requester.apiService().getTopicDetail(topicId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onGetTopic(it.topic, true)
            }) {
                view?.onGetTopic(null, true)
            }
        runDisposable(disposable)
    }

    override fun loadReplies(topicId: Long, isRefresh: Boolean) {
        //load from api
        if (isRefresh) {
            pageIndex = 0
        }
        val disposable = Requester.apiService().getTopicReplies(topicId, pageIndex, pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                pageIndex++
                view?.onGetReplies(it.list, true)
            }) {
                view?.onGetReplies(null, true)
            }
        runDisposable(disposable)
    }

    override fun deleteReply(replyId: Long) {
        val disposable = Requester.apiService().deleteTopicReply(replyId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                pageIndex++
                view?.onDeleteReply(replyId)
            }) {
                view?.onDeleteReply(-1L)
            }
        runDisposable(disposable)
    }
}