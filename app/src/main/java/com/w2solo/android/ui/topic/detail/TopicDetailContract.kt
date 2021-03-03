package com.w2solo.android.ui.topic.detail

import com.w2solo.android.data.entity.Comment
import com.w2solo.android.data.entity.Topic
import com.w2solo.android.mvp.IPresenter
import com.w2solo.android.mvp.IView

interface TopicDetailContract {

    interface View : IView {

        fun onGetTopic(topic: Topic?, fromAPI: Boolean)

        fun onGetReplies(dataList: List<Comment>?, isRefresh: Boolean)
    }

    interface Presenter : IPresenter<View> {

        fun loadTopic(topicId:Long)

        fun loadReplies(isRefresh: Boolean)
    }
}