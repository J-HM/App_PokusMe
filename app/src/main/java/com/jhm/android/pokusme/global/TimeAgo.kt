package com.jhm.android.pokusme.global

import com.google.firebase.Timestamp

object TimeAgo {
    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS
    
    fun getTimeAgo(timeStamp: Timestamp?): String {
        if (timeStamp == null) return "-"
        
        val time = timeStamp.seconds * 1000
        
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) return "-"
        
        val diff = now - time
        return when {
            diff < 10 * SECOND_MILLIS -> "방금 전"
            diff < 60 * SECOND_MILLIS -> (diff / SECOND_MILLIS).toString() + "초 전"
            diff < 60 * MINUTE_MILLIS -> (diff / MINUTE_MILLIS).toString() + "분 전"
            diff < 24 * HOUR_MILLIS -> (diff / HOUR_MILLIS).toString() + "시간 전"
            diff < 2 * DAY_MILLIS -> "어제"
            else -> (diff / DAY_MILLIS).toString() + "일 전"
        }
    }
}