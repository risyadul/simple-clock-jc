package com.example.simpleclock.data.repository

import com.example.simpleclock.domain.model.TimeData
import com.example.simpleclock.domain.repository.TimeRepository
import java.text.SimpleDateFormat
import java.util.*

class TimeRepositoryImpl : TimeRepository {
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

    override fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }
} 