package com.w2solo.android.ui.topic.list

import com.w2solo.android.data.entity.Topic
import com.w2solo.android.mvp.IPresenter
import com.w2solo.android.mvp.IView

interface TopicListContract {

    interface View : IView {
        fun onGetList(newList: List<Topic>?, isRefresh: Boolean)
    }

    interface Presenter : IPresenter<View> {
        fun loadList(isRefresh: Boolean)
    }
}