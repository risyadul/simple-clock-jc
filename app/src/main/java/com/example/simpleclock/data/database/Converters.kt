package com.example.simpleclock.data.database

import androidx.room.TypeConverter
import java.time.DayOfWeek

class Converters {
    @TypeConverter
    fun fromString(value: String): Set<DayOfWeek> {
        if (value.isEmpty()) return emptySet()
        return value.split(",")
            .filter { it.isNotEmpty() }
            .map { DayOfWeek.valueOf(it) }
            .toSet()
    }

    @TypeConverter
    fun toString(days: Set<DayOfWeek>): String {
        return days.joinToString(",") { it.name }
    }
} 