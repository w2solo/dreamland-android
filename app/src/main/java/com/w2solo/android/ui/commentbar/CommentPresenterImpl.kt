package com.w2solo.android.ui.commentbar

import com.w2solo.android.http.Requester
import com.w2solo.android.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommentPresenterImpl(view: CommentBarContract.View) :
    BasePresenter<CommentBarContract.View>(view), CommentBarContract.Presenter {

    override fun sendReply(topicId: Long, content: String) {
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

    override fun editReply(replyId: Long, content: String) {
        val disposable = Requester.apiService().editTopicReply(replyId, content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onEditCommentFinished(it.comment)
            }) {
                view?.onEditCommentFinished(null)
            }
        runDisposable(disposable)
    }
}