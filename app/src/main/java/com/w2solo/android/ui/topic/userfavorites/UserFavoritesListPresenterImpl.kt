package com.w2solo.android.ui.topic.userfavorites

import com.w2solo.android.http.Requester
import com.w2solo.android.ui.topic.list.AbsTopicListPresenterImpl
import com.w2solo.android.ui.topic.list.TopicListContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserFavoritesListPresenterImpl(
    private val userLogin: String,
    view: TopicListContract.View
) : AbsTopicListPresenterImpl(view) {

    override fun doLoad(isRefresh: Boolean, offset: Int, limit: Int) {
        val disposable = Requester.apiService().getFavoriteList(userLogin, offset, limit)
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