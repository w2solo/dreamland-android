package com.w2solo.android.http.result

import com.google.gson.annotations.SerializedName
import com.w2solo.android.data.entity.Comment

class ReplyBean : IBean {
    @SerializedName("reply")
    var comment: Comment? = null
}