package com.w2solo.android.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class UserMeta() : Parcelable {

    @SerializedName("followed")
    var followed: Boolean = false

    @SerializedName("blocked")
    var blocked: Boolean = false

    constructor(parcel: Parcel) : this() {
        followed = parcel.readByte() != 0.toByte()
        blocked = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeByte(if (followed) 1 else 0)
        dest?.writeByte(if (blocked) 1 else 0)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<UserMeta> {
        override fun createFromParcel(parcel: Parcel): UserMeta {
            return UserMeta(parcel)
        }

        override fun newArray(size: Int): Array<UserMeta?> {
            return arrayOfNulls(size)
        }
    }
}