package com.w2solo.android.ui.user.userinfo

import com.w2solo.android.http.Requester
import com.w2solo.android.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserInfoPresenterImpl(view: UserInfoContract.View) :
    BasePresenter<UserInfoContract.View>(view), UserInfoContract.Presenter {

    override fun loadUserInfo(userLogin: String) {
        val disposable = Requester.apiService().getUserInfo(userLogin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onGetInfo(it.user, it.meta)
            }) {
                view?.onGetInfo(null, null)
            }
        runDisposable(disposable)
    }

    override fun followOrNot(userLogin: String, followOrNot: Boolean) {
        val observable = if (followOrNot) {
            Requester.apiService().followUser(userLogin)
        } else {
            Requester.apiService().unFollowUser(userLogin)
        }
        val disposable = observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onFollowOrNot(true, followOrNot)
            }) {
                view?.onFollowOrNot(isSuccess = false, newFollowStatus = false)
            }
        runDisposable(disposable)
    }
}