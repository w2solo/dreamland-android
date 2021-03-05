package com.w2solo.android.utils

import android.content.Context
import com.w2solo.android.R
import java.text.SimpleDateFormat
import java.util.*

object CalendarUtil {

    const val HOUR_IN_SEC = 3600
    const val DAY_IN_SEC = 24 * HOUR_IN_SEC

    fun parseTime(source: String): Calendar? {
        val formatStr = "yyyy-MM-dd HH:mm:ss"
        if (source.length < formatStr.length) {
            return null
        }
        var result = source.replace("T", " ")
        result = result.substring(0, formatStr.length)

        val format = SimpleDateFormat(formatStr)
        val date: Date = format.parse(result)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    fun parseTimeForRead(context: Context, cal: Calendar?): String {
        if (cal == null) {
            return ""
        }
        val now = Calendar.getInstance()
        val nowTime = now.timeInMillis / 1000
        val calTime = cal.timeInMillis / 1000
        val diffInSec = nowTime - calTime

        return when {
            diffInSec < 300 -> context.getString(R.string.comment_time_just_now)
            diffInSec < 3600 -> {
                val minutes = diffInSec / 60
                context.getString(R.string.comment_time_in_minutes, minutes)
            }
            diffInSec < DAY_IN_SEC -> {
                val hours = diffInSec / HOUR_IN_SEC
                context.getString(R.string.comment_time_in_hours, hours)
            }
            else -> SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(cal.time)
        }
    }
}