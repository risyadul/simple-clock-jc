package com.example.simpleclock.domain.model

data class TimerState(
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0,
    val isRunning: Boolean = false
) {
    val totalSeconds: Int
        get() = hours * 3600 + minutes * 60 + seconds
} 