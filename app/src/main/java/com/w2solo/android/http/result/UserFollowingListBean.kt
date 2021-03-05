package com.w2solo.android.http.result

import com.google.gson.annotations.SerializedName
import com.w2solo.android.data.entity.User

class UserFollowingListBean : IBean {
    @SerializedName("following")
    var list: List<User>? = null
}