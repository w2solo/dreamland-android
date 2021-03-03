package com.w2solo.android.ui.topic.detail

import com.w2solo.android.http.Requester
import com.w2solo.android.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TopicDetailPresenterImpl(view: TopicDetailContract.View) :
    BasePresenter<TopicDetailContract.View>(view), TopicDetailContract.Presenter {

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

    override fun loadReplies(isRefresh: Boolean) {
    }
}