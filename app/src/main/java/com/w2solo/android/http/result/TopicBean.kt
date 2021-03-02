package com.w2solo.android.http.result

import com.google.gson.annotations.SerializedName
import com.w2solo.android.data.entity.Topic

class TopicBean : IBean {
    @SerializedName("topic")
    var topic: Topic? = null
}