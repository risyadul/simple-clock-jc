package com.example.simpleclock.domain.model

import java.time.DayOfWeek

data class Alarm(
    val id: Int = 0,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean = true,
    val label: String = "",
    val repeatDays: Set<DayOfWeek> = emptySet(),
    val vibrate: Boolean = true,
    val soundUri: String? = null
)

data class AlarmState(
    val alarms: List<Alarm> = emptyList(),
    val isEditing: Boolean = false,
    val selectedAlarm: Alarm? = null
) 