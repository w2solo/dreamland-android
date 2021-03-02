package com.w2solo.android.http.result

import com.google.gson.annotations.SerializedName
import com.w2solo.android.data.entity.User

class UserBean : IBean {
    @SerializedName("user")
    var user: User? = null
}