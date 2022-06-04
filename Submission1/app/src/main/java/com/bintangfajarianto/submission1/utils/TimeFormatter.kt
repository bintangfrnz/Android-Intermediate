package com.bintangfajarianto.submission1.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {

    fun getTimeAgo(dateFormatted: String) : String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val date = sdf.parse(dateFormatted)?.time
        val current = System.currentTimeMillis()

        return date?.let { time ->
            DateUtils.getRelativeTimeSpanString(
                time, current,  DateUtils.MINUTE_IN_MILLIS
            )
        } as String
    }
}