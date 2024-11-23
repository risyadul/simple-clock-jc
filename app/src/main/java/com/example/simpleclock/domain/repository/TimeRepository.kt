package com.example.simpleclock.domain.repository

import com.example.simpleclock.domain.model.TimeData

interface TimeRepository {
    fun getCurrentTime(): TimeData
    fun getCurrentDate(): String
} 