package com.w2solo.android.ui.topic.list

import com.w2solo.android.data.entity.Topic
import com.w2solo.android.mvp.IPresenter
import com.w2solo.android.mvp.IView

interface TopicListContract {

    interface View : IView {
        fun onGetList(newList: List<Topic>?, isRefresh: Boolean)
    }

    interface Presenter : IPresenter<View> {
//        fun loadListByNode(isRefresh: Boolean, nodeId: Long)
//
//        fun loadListByUser(isRefresh: Boolean, userLogin: String)

        fun loadList(isRefresh: Boolean)
    }
}