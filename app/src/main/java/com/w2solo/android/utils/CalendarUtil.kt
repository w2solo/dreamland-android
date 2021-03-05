package com.w2solo.android.utils

object CalendarUtil {
    fun parseTime(source: String): String {
        val format = "yyyy-MM-dd HH:mm"
        val result = source.replace("T", " ")
        return result.substring(0, format.length)
    }
}