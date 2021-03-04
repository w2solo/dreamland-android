package com.w2solo.android.data.entity

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

class Comment() : Parcelable {

    @SerializedName("id")
    var id: Long = 0

    @SerializedName("body")
    var body: String? = null

    @SerializedName("topic_id")
    var topicId: Long = -1L

    @SerializedName("action")
    var action: String? = null

    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    lateinit var createdTime: String

    @SerializedName("deleted")
    @ColumnInfo(name = "deleted")
    var isDeleted: Boolean = false

    @SerializedName("user")
    var user: User? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        body = parcel.readString()
        createdTime = parcel.readString()!!
        isDeleted = parcel.readInt() == 1
        user = parcel.readParcelable(User::class.java.classLoader)
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeString(body)
        dest?.writeString(createdTime)
        dest?.writeInt(if (isDeleted) 1 else 0)
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

    fun canAction(): Boolean {
        return TextUtils.isEmpty(action) && !isDeleted
    }

    fun canShow(): Boolean =
        !isDeleted && (TextUtils.isEmpty(action) ||
                (!TextUtils.equals(action, Action.Ban)
                        && !TextUtils.equals(action, Action.Excellent)))
}