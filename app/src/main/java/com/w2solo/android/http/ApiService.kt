package com.w2solo.android.http

import com.w2solo.android.http.result.TopicBean
import com.w2solo.android.http.result.TopicListBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        //此类接口的基地址
        val baseUrl = "https://indiehackers.net/api/v3/"
    }

    //话题列表：最新
    @GET("topics?type=recent")
    fun getTopicList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Observable<TopicListBean>

    //话题详情
    @GET("topics/")
    fun getTopicDetails(@Query("topic_id") topic_id: Int): Observable<TopicBean>
}