package com.example.simpleclock.domain.usecase

import com.example.simpleclock.domain.model.TimeData
import com.example.simpleclock.domain.repository.TimeRepository

class GetTimeUseCase(private val repository: TimeRepository) {
    operator fun invoke(): TimeData = repository.getCurrentTime()
} 