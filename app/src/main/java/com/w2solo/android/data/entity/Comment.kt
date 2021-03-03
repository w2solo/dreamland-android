package com.w2solo.android.data.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

class Comment() : Parcelable {
    @SerializedName("id")
    var id: Long = 0

    @SerializedName("body")
    var body: String? = null

    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    lateinit var createdTime: String

    @SerializedName("user")
    var user: User? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        body = parcel.readString()
        createdTime = parcel.readString()!!
        user = parcel.readParcelable(User::class.java.classLoader)
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeString(body)
        dest?.writeString(createdTime)
        dest?.writeParcelable(user, flags)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }
}