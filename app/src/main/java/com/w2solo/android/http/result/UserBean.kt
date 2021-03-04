package com.w2solo.android.http.result

import com.google.gson.annotations.SerializedName
import com.w2solo.android.data.entity.User
import com.w2solo.android.data.entity.UserMeta

class UserBean : IBean {
    @SerializedName("user")
    var user: User? = null

    @SerializedName("meta")
    var meta: UserMeta? = null
}