package com.w2solo.android.http

import com.w2solo.android.app.account.Token
import com.w2solo.android.http.result.*
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

    @FormUrlEncoded
    @POST("oauth/token?")
    fun refreshAccessToken(
        @Field("refresh_token") refresh_token: String,
        @Field("client_id") client_id: String,
        @Field("client_secret") client_secret: String,
        @Field("grant_type") grant_type: String = "refresh_token"
    ): Observable<Token>

    @GET("api/v3/users/me")
    fun getLoginUserInfo(@Header("Authorization") token: String): Observable<UserBean>

    @GET("api/v3/users/{login}")
    fun getUserInfo(@Path("login") login: String): Observable<UserBean>

    @POST("api/v3/users/{follow_user}/follow")
    fun followUser(@Path("follow_user") follow_user: String): Observable<UserBean>

    @POST("api/v3/users/{follow_user}/unfollow")
    fun unFollowUser(@Path("follow_user") follow_user: String): Observable<UserBean>

    //话题列表：最新
    @GET("api/v3/topics?type=last")
    fun getTopicList(
        @Query("offset") offset: Int, @Query("limit") limit: Int
    ): Observable<TopicListBean>

    //话题列表：最新
    @GET("api/v3/topics?type=last")
    fun getTopicListByNode(
        @Query("node_id") nodeId: Long,
        @Query("offset") offset: Int, @Query("limit") limit: Int
    ): Observable<TopicListBean>

    //话题列表：最新
    @GET("api/v3/users/{login}/topics")
    fun getTopicListByUser(
        @Path("login") login: String
    ): Observable<TopicListBean>

    //话题详情
    @GET("api/v3/topics/{topic_id}")
    fun getTopicDetail(@Path("topic_id") topic_id: Long): Observable<TopicBean>

    //话题详情
    @GET("api/v3/topics/{topic_id}/replies")
    fun getTopicReplies(
        @Path("topic_id") topic_id: Long,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Observable<ReplyListBean>

    @FormUrlEncoded
    //发布帖子
    @POST("api/v3/topics/{topic_id}/replies")
    fun sendTopicReply(
        @Path("topic_id") topic_id: Long,
        @Field("body") body: String,
    ): Observable<ReplyBean>

    @FormUrlEncoded
    //修改帖子
    @POST("api/v3/replies/{reply_id}")
    fun editTopicReply(
        @Path("reply_id") reply_id: Long,
        @Field("body") body: String,
    ): Observable<ReplyBean>

    //删除评论
    @DELETE("api/v3/replies/{reply_id}")
    fun deleteTopicReply(@Path("reply_id") reply_id: Long): Observable<ReplyBean>

    //热门用户
    @GET("api/v3/users")
    fun getTopUsers(): Observable<UserListBean>

    //节点列表
    @GET("api/v3/nodes")
    fun getNodeList(): Observable<NodeListBean>
}