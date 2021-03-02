package com.w2solo.android.ui.account

import com.w2solo.android.app.AppConfigs
import com.w2solo.android.app.account.AccountManager
import com.w2solo.android.app.account.Token
import com.w2solo.android.data.entity.User
import com.w2solo.android.http.Requester
import com.w2solo.android.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginPresenterImpl(view: LoginContract.View) : BasePresenter<LoginContract.View>(view),
    LoginContract.Presenter {

    override fun startLogin(account: String, pwd: String) {
        val disposable = Requester.apiService()
            .getAccessToken(account, pwd, AppConfigs.CLIENT_ID, AppConfigs.CLIENT_SECRET)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getUserInfo(it)
            }) {
                view?.onLogin(null)
            }
        runDisposable(disposable)
    }

    private fun getUserInfo(token: Token) {
        val disposable = Requester.apiService()
            .getLoginUserInfo("Bearer ${token.accessToken}")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onLoginSuccess(token, it.user!!)
            }) {
                view?.onLogin(null)
            }
        runDisposable(disposable)
    }

    private fun onLoginSuccess(token: Token, user: User) {
        AccountManager.getInstance().onLogin(user, token)
        view?.onLogin(user)
    }
}