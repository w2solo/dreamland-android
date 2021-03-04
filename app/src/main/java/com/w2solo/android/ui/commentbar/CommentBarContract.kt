package com.w2solo.android.ui.commentbar

import com.w2solo.android.data.entity.Comment
import com.w2solo.android.mvp.IPresenter
import com.w2solo.android.mvp.IView

interface CommentBarContract {

    interface View : IView {

        fun onCommentFinished(comment: Comment?)

        fun onEditCommentFinished(comment: Comment?)
    }

    interface Presenter : IPresenter<View> {
        fun sendReply(topicId: Long, content: String)

        fun editReply(replyId: Long, content: String)
    }
}