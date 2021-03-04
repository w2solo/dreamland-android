package com.w2solo.android.ui.user.userreplies

import com.w2solo.android.http.Requester
import com.w2solo.android.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepliesPresenterImpl(view: UserRepliesContract.View?) :
    BasePresenter<UserRepliesContract.View>(view), UserRepliesContract.Presenter {

    override fun loadList(isRefresh: Boolean, userLogin: String) {
        val disposable = Requester.apiService().getUserReplies(userLogin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onGetList(it.list, isRefresh)
            }) {
                view?.onGetList(null, isRefresh)
            }
        runDisposable(disposable)
    }
}