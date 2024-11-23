package com.example.simpleclock.data.repository

import com.example.simpleclock.domain.model.TimeData
import com.example.simpleclock.domain.repository.TimeRepository
import java.text.SimpleDateFormat
import java.util.*

/**
 * Implementation of TimeRepository that provides current time and date information
 * using Java's Calendar and SimpleDateFormat utilities
 *
 * Features:
 * - Provides formatted time data including hours, minutes, seconds, and AM/PM indicator
 * - Provides formatted date string with day of week and date
 * - Uses device's locale for formatting
 */
class TimeRepositoryImpl : TimeRepository {

    /**
     * Gets the current time formatted as TimeData object
     *
     * Features:
     * - Hours in 12-hour format (01-12)
     * - Minutes (00-59)
     * - Seconds (00-59)
     * - AM/PM indicator in lowercase
     *
     * @return TimeData object containing formatted time components
     */
    override fun getCurrentTime(): TimeData {
        val calendar = Calendar.getInstance()
        return TimeData(
            hours = String.format("%02d", calendar.get(Calendar.HOUR)),
            minutes = String.format("%02d", calendar.get(Calendar.MINUTE)),
            seconds = String.format("%02d", calendar.get(Calendar.SECOND)),
            amPm = SimpleDateFormat("a", Locale.getDefault())
                .format(calendar.time)
                .lowercase()
        )
    }

    /**
     * Gets the current date formatted as a string
     *
     * Format: "Day of week, DD Month"
     * Example: "Monday, 15 January"
     *
     * Note: Uses device's locale for day and month names
     *
     * @return Formatted date string
     */
    override fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }
} 