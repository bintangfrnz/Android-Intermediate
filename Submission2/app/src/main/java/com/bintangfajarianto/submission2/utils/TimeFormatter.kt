package com.bintangfajarianto.submission2.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {

    fun getTimeAgo(dateFormatted: String) : String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = sdf.parse(dateFormatted)?.time
        val current = System.currentTimeMillis()

        return date?.let { time ->
            DateUtils.getRelativeTimeSpanString(
                time, current,  DateUtils.MINUTE_IN_MILLIS
            )
        } as String
    }
}