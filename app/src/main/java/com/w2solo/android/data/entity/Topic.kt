package com.w2solo.android.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "topic")
data class Topic(
    @SerializedName("title")
    @ColumnInfo(name = "title") val title: String,

    @SerializedName("created_at")
    @ColumnInfo(name = "created_at") val createdTime: String,

    @SerializedName("replied_at")
    @ColumnInfo(name = "replied_at") val repliedTime: String,

    @SerializedName("replies_count")
    @ColumnInfo(name = "replies_count") val repliesCount: Int,

    @SerializedName("likes_count")
    @ColumnInfo(name = "likes_count") val likesCount: Int,

    @SerializedName("node_name")
    @ColumnInfo(name = "node_name") val nodeName: String,

    @SerializedName("node_id")
    @ColumnInfo(name = "node_id") val nodeId: Int,

    @SerializedName("grade")
    @ColumnInfo(name = "grade") val grade: String,

    @SerializedName("excellent")
    @ColumnInfo(name = "excellent") val excellent: Int,

    @SerializedName("body")
    @ColumnInfo(name = "body") val body: String,

    @SerializedName("hits")
    @ColumnInfo(name = "hits") val hits: Int,

    @SerializedName("user")
    @Embedded val user: User?
) {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0
}