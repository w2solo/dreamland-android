package com.w2solo.android.http.result

import com.google.gson.annotations.SerializedName
import com.w2solo.android.data.entity.Node

class NodeListBean : IBean {
    @SerializedName("nodes")
    var list: List<Node>? = null
}