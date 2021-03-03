package com.w2solo.android.data.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "topic")

class Topic() : Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0L

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String? = null

    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    var createdTime: String? = null

    @SerializedName("replied_at")
    @ColumnInfo(name = "replied_at")
    var repliedTime: String? = null

    @SerializedName("replies_count")
    @ColumnInfo(name = "replies_count")
    var repliesCount: Int = 0

    @SerializedName("likes_count")
    @ColumnInfo(name = "likes_count")
    var likesCount: Int = 0

    @SerializedName("node_name")
    @ColumnInfo(name = "node_name")
    var nodeName: String? = null

    @SerializedName("node_id")
    @ColumnInfo(name = "node_id")
    var nodeId: Int = 0

    @SerializedName("grade")
    @ColumnInfo(name = "grade")
    var grade: String? = null

    @SerializedName("excellent")
    @ColumnInfo(name = "excellent")
    var excellent: Int = 0

    @SerializedName("body")
    @ColumnInfo(name = "body")
    var body: String? = null

    @SerializedName("body_html")
    @ColumnInfo(name = "body_html")
    var bodyHtml: String? = null

    @SerializedName("hits")
    @ColumnInfo(name = "hits")
    var hits: Int = 0

    @SerializedName("user")
    @Embedded
    var user: User? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        title = parcel.readString()
        createdTime = parcel.readString()
        repliedTime = parcel.readString()
        repliesCount = parcel.readInt()
        likesCount = parcel.readInt()
        nodeName = parcel.readString()
        nodeId = parcel.readInt()
        grade = parcel.readString()
        excellent = parcel.readInt()
        body = parcel.readString()
        bodyHtml = parcel.readString()
        hits = parcel.readInt()
        user = parcel.readParcelable(User::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(createdTime)
        parcel.writeString(repliedTime)
        parcel.writeInt(repliesCount)
        parcel.writeInt(likesCount)
        parcel.writeString(nodeName)
        parcel.writeInt(nodeId)
        parcel.writeString(grade)
        parcel.writeInt(excellent)
        parcel.writeString(body)
        parcel.writeString(bodyHtml)
        parcel.writeInt(hits)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Topic> {
        override fun createFromParcel(parcel: Parcel): Topic {
            return Topic(parcel)
        }

        override fun newArray(size: Int): Array<Topic?> {
            return arrayOfNulls(size)
        }
    }
}