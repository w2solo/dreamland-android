package com.w2solo.android.http

import com.w2solo.android.utils.Logger
import okhttp3.Interceptor
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*

class LogInterceptor : Interceptor {
    val tag = "Retrofit"
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        Logger.i(
            tag,
            format.format(Date()) + " Requeste " + "\nmethod:" + request.method() + "\nurl:" + request.url() + "\nbody:" + request.body()
        )
        val response = chain.proceed(request)
        //response.peekBody不会关闭流
        Logger.i(
            tag,
            format.format(Date()) + " Response " + "\nsuccessful:" + response.isSuccessful + "\nbody:" + response.peekBody(
                1024
            )?.string()
        )
        return response
    }
}