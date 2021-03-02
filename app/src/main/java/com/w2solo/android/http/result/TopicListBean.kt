package com.w2solo.android.http.result

import com.google.gson.annotations.SerializedName
import com.w2solo.android.data.entity.Topic

class TopicListBean : IBean {
    @SerializedName("topics")
    var list: List<Topic>? = null
}