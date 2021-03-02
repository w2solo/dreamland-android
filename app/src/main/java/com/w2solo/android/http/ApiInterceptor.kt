package com.w2solo.android.http

import com.w2solo.android.app.account.AccountManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val token = AccountManager.getInstance().userToken
        //同一添加 Accept
        val builder = original.newBuilder()
            .addHeader("Accept", "application/json; charset=utf-8")
        if (token != null) {
            builder.addHeader("Authorization", "Bearer ${token.refreshToken}")
        }
        return chain.proceed(builder.build())
    }
}