package com.w2solo.android.app.account

import com.google.gson.annotations.SerializedName
import java.util.*

class Token {
    @SerializedName("access_token")
    var accessToken: String? = null

    @SerializedName("refresh_token")
    var refreshToken: String? = null

    @SerializedName("created_at")
    var createAt: Long = 0

    @SerializedName("expires_in")
    var expiresIn: Long = 0

    fun isExpired(): Boolean {
        val now = Calendar.getInstance().timeInMillis / 1000
        val expireEnd = expiresIn + createAt
        return expireEnd < now
    }
}