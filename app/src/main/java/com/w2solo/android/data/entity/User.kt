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

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        login = parcel.readString()
        name = parcel.readString()
        avatar_url = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeString(login)
        dest?.writeString(name)
        dest?.writeString(avatar_url)
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