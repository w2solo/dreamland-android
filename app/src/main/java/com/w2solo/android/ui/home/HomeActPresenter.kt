package com.w2solo.android.ui.home

import com.w2solo.android.app.AppConfigs
import com.w2solo.android.app.account.AccountManager
import com.w2solo.android.http.Requester
import com.w2solo.android.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class HomeActPresenter(view: HomeActContract.View) : BasePresenter<HomeActContract.View>(view),
    HomeActContract.Presenter {

    override fun init() {
        checkAccount()
    }

    private fun checkAccount() {
        if (!AccountManager.getInstance().isLogin) {
            return
        }
        val token = AccountManager.getInstance().userToken
        if (!token!!.isExpired()) {
            return
        }
        val disposable = Requester.apiService()
            .refreshAccessToken(
                token.refreshToken ?: "",
                AppConfigs.CLIENT_ID,
                AppConfigs.CLIENT_SECRET
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                AccountManager.getInstance().updateToken(it)
            }) {
                if (it is HttpException) {
                    val code = it.code()
                    if (code == 400) {
                        AccountManager.getInstance().logout()
                    }
                }
                it.printStackTrace()
            }
        runDisposable(disposable)
    }
}