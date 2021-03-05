package com.w2solo.android.ui.user.follow

import com.w2solo.android.http.Requester
import com.w2solo.android.ui.user.baselist.AbsUserListPresenter
import com.w2solo.android.ui.user.baselist.AbsUsersListContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserFollowListPresenterImpl(private val userLogin: String, view: AbsUsersListContract.View) :
    AbsUserListPresenter(view) {

    private var isLoading = false
    private var pageIndex = 0
    private var pageSize = 50
    private var hasMore = true

    override fun loadList(isRefresh: Boolean) {
        if (isLoading) {
            return
        }
        if (isRefresh) {
            pageIndex = 0
        } else {
            if (!hasMore) {
                return
            }
        }
        val offset = pageIndex * pageSize
        val disposable = Requester.apiService()
            .getFollowingList(userLogin, offset, pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading = false
                hasMore = it.list!!.size >= pageSize
                pageIndex++
                view?.onGetList(it.list, isRefresh)
            }) {
                isLoading = false
                view?.onGetList(null, isRefresh)
            }
        runDisposable(disposable)
    }
}