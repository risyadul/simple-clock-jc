package com.example.simpleclock.domain.usecase

import com.example.simpleclock.domain.repository.TimeRepository

class GetDateUseCase(private val repository: TimeRepository) {
    operator fun invoke(): String = repository.getCurrentDate()
} 