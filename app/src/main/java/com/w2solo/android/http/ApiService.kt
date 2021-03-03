package com.w2solo.android.http

import com.w2solo.android.app.account.Token
import com.w2solo.android.http.result.TopicBean
import com.w2solo.android.http.result.TopicListBean
import com.w2solo.android.http.result.UserBean
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    companion object {
        //此类接口的基地址
        val baseUrl = "https://w2solo.com/"
    }

    //话题详情
    @GET("api/v3/topics/")
    fun getTopicDetails(@Query("topic_id") topic_id: Int): Observable<TopicBean>

    @FormUrlEncoded
    @POST("oauth/token?")
    fun getAccessToken(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("client_id") client_id: String,
        @Field("client_secret") client_secret: String,
        @Field("grant_type") grant_type: String = "password"
    ): Observable<Token>

    @GET("api/v3/users/me")
    fun getLoginUserInfo(@Header("Authorization") token: String): Observable<UserBean>

    //话题列表：最新
    @GET("api/v3/topics?type=last")
    fun getTopicList(
        @Query("offset") offset: Int, @Query("limit") limit: Int
    ): Observable<TopicListBean>

    //话题详情
    @GET("api/v3/topics/{topic_id}")
    fun getTopicDetail(@Path("topic_id") topic_id: Long): Observable<TopicBean>
}