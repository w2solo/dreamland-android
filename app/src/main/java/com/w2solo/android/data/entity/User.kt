package com.w2solo.android.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.w2solo.android.app.account.IAccount

class User() : IAccount, Parcelable {

    @SerializedName("id")
    var id: Long = 0

    @SerializedName("login")
    var login: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("avatar_url")
    var avatar_url: String? = null

    @SerializedName("location")
    var location: String? = null

    @SerializedName("topics_count")
    var topicsCount: Int = 0

    @SerializedName("replies_count")
    var repliesCount: Int = 0

    @SerializedName("following_count")
    var followingCount: Int = 0

    @SerializedName("followers_count")
    var followersCount: Int = 0

    @SerializedName("favorites_count")
    var favoritesCount: Int = 0

    @SerializedName("level_name")
    var levelName: String? = null

    @SerializedName("bio")
    var bio: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("meta")
    var meta: UserMeta? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        login = parcel.readString()
        name = parcel.readString()
        avatar_url = parcel.readString()
        location = parcel.readString()

        topicsCount = parcel.readInt()
        repliesCount = parcel.readInt()
        followingCount = parcel.readInt()
        followersCount = parcel.readInt()
        favoritesCount = parcel.readInt()

        levelName = parcel.readString()
        bio = parcel.readString()
        email = parcel.readString()
        meta = parcel.readParcelable(UserMeta::class.java.classLoader)
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeString(login)
        dest?.writeString(name)
        dest?.writeString(avatar_url)
        dest?.writeString(location)

        dest?.writeInt(topicsCount)
        dest?.writeInt(repliesCount)
        dest?.writeInt(followingCount)
        dest?.writeInt(followersCount)
        dest?.writeInt(favoritesCount)

        dest?.writeString(levelName)
        dest?.writeString(bio)
        dest?.writeString(email)
        dest?.writeParcelable(meta, flags)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}