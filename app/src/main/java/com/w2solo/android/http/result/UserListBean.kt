package com.w2solo.android.http.result

import com.google.gson.annotations.SerializedName
import com.w2solo.android.data.entity.User

class UserListBean : IBean {
    @SerializedName("users")
    var list: List<User>? = null
}