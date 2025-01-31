package io.proxima.breathe.core.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object TimestampUtils {

    /**
     * A function to find the hours difference between two timestamp values.
     *
     * @param start The lower timestamp in milliseconds.
     * @param end The larger timestamp in milliseconds.
     */
    fun findHoursDifference(
        start: Long,
        end: Long
    ): Int {
        val differenceInMillis = end - start
        val differenceInHours = differenceInMillis / (1000 * 60 * 60)
        return differenceInHours.toInt()
    }

    /**
     * Extract the day of the current month.
     * @param timestamp The timestamp in milliseconds.
     */
    fun extractDayOfTheMonth(timestamp: Long): Int {
        val instant = Instant.ofEpochMilli(timestamp)
        val day = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).dayOfMonth
        return day
    }

    /**
     * Extract the month name of the current year in ***`MMM`*** format.
     * @param timestamp The timestamp in milliseconds.
     */
    fun extractMonthOfTheYear(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp)
        val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
        val dateFormatter = DateTimeFormatter.ofPattern("MMM", Locale.getDefault())
        return zonedDateTime.format(dateFormatter)
    }

}