package com.w2solo.android.ui.commentbar

import com.w2solo.android.http.Requester
import com.w2solo.android.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommentPresenterImpl(view: CommentBarContract.View) :
    BasePresenter<CommentBarContract.View>(view), CommentBarContract.Presenter {

    override fun sendReplay(topicId: Long, content: String) {
        val disposable = Requester.apiService().sendTopicReply(topicId, content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onCommentFinished(it.comment)
            }) {
                view?.onCommentFinished(null)
            }
        runDisposable(disposable)

    }
}