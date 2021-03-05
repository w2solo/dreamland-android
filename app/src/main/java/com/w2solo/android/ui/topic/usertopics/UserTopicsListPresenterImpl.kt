package com.w2solo.android.ui.topic.usertopics

import com.w2solo.android.http.Requester
import com.w2solo.android.ui.topic.list.AbsTopicListPresenterImpl
import com.w2solo.android.ui.topic.list.TopicListContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserTopicsListPresenterImpl(private val userLogin: String, view: TopicListContract.View) :
    AbsTopicListPresenterImpl(view) {

    override fun doLoad(isRefresh: Boolean, offset: Int, limit: Int) {
        val disposable = Requester.apiService().getTopicListByUser(userLogin)
            .subscribeOn(Schedulers.io())
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