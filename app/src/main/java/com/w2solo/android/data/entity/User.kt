package com.w2solo.android.data.entity

import com.google.gson.annotations.SerializedName
import com.w2solo.android.app.account.IAccount

public class User : IAccount {

    @SerializedName("id")
    var id: Long = 0

    @SerializedName("login")
    var login: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("avatar_url")
    var avatar_url: String? = null
}