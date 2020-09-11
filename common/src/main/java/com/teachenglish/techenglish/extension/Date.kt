package com.teachenglish.techenglish.extension

import io.reactivex.annotations.Nullable
import java.text.SimpleDateFormat
import java.util.*


fun Date.toDateString(date: Date,format: String = "yyyy-MM-dd"): String {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).format(date)
    } catch (e: Exception) {
        ""
    }
}
fun dateToStringFormat(date: Date, @Nullable format: String?): String {
    var format = format
    try {
        if (format == null)
            format = "yyyy-MM-dd HH:mm"
        return SimpleDateFormat(format, Locale.getDefault()).format(date)
    } catch (e: Exception) {
        return ""
    }

}