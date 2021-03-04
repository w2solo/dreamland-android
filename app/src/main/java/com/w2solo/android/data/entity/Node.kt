package com.w2solo.android.data.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class Node {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Long = 0L

    @SerializedName("topics_count")
    var topicsCount: Int = 0

    @SerializedName("summary")
    var summary: String? = null

    @SerializedName("name")
    var name: String? = null
}