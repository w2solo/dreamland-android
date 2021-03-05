package com.w2solo.android.ui.user.topusers

import com.w2solo.android.http.Requester
import com.w2solo.android.ui.user.baselist.AbsUserListPresenter
import com.w2solo.android.ui.user.baselist.AbsUsersListContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TopUserListPresenterImpl(view: AbsUsersListContract.View) : AbsUserListPresenter(view) {

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