package com.example.simpleclock.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean,
    val label: String,
    val repeatDays: String, // Stored as comma-separated days
    val vibrate: Boolean,
    val soundUri: String?
) 