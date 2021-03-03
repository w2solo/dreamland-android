package com.w2solo.android.http.result

import com.google.gson.annotations.SerializedName
import com.w2solo.android.data.entity.Comment

class ReplyListBean : IBean {
    @SerializedName("replies")
    var list: List<Comment>? = null
}