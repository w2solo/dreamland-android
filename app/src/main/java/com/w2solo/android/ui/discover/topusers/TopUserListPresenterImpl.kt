package com.w2solo.android.ui.discover.topusers

import com.w2solo.android.http.Requester
import com.w2solo.android.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TopUserListPresenterImpl(view: TopUsersListContract.View) :
    BasePresenter<TopUsersListContract.View>(view), TopUsersListContract.Presenter {

    override fun loadList(isRefresh: Boolean) {
        val disposable = Requester.apiService().getTopUsers().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onGetList(it.list, true)
            }) {
                view?.onGetList(null, true)
            }
        runDisposable(disposable)
    }
}