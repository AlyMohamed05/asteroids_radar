package com.silverbullet.asteroidsradar.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeUtils {
    val today: String
        get() = getDate()

    val upcomingWeek: List<String>
        get() = getUpComingWeekDays()

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    /**
     * If 0 days or no days are passed, it return today date
     * else it adds the value of days
     */
    private fun getDate(days: Long? = null): String {
        var current = LocalDateTime.now()
        if (days != null) {
            current = current.plusDays(days)
        }
        return current.format(formatter)
    }

    /**
     * It Returns The 7 upcoming days from today
     */
    private fun getUpComingWeekDays(): List<String> {
        val days = mutableListOf<String>()
        repeat(7) {
            days.add(getDate(it.toLong()))
        }
        return days.toList()
    }
}