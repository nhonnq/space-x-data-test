package dev.nhonnq.test.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateTimeUtil {

    private const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    /**
     * Input: "2018-12-14T09:55:00"
     * Output:
     */
    @SuppressLint("SimpleDateFormat")
    fun getTimeFormatUtc(input: String): String {
        val parser = SimpleDateFormat(DATE_TIME_FORMAT)
        val formatter = SimpleDateFormat("dd MMM yyyy - HH:mm 'UTC'")
        return parser.parse(input)?.let { formatter.format(it) } ?: ""
    }

}