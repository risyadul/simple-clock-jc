package com.example.simpleclock.presentation.screen.clock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleclock.domain.model.TimeData
import com.example.simpleclock.domain.usecase.GetDateUseCase
import com.example.simpleclock.domain.usecase.GetTimeUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing clock-related data and updates
 *
 * Features:
 * - Provides current time updates every second
 * - Manages current date information
 * - Uses coroutines for background time updates
 *
 * @property getTimeUseCase Use case to retrieve formatted time data
 * @property getDateUseCase Use case to retrieve formatted date string
 */
class ClockViewModel(
    private val getTimeUseCase: GetTimeUseCase,
    getDateUseCase: GetDateUseCase
) : ViewModel() {

    private val _currentTime = MutableStateFlow(getTimeUseCase())
    /**
     * Observable state of the current time
     * Updates every second with hours, minutes, and seconds
     */
    val currentTime: StateFlow<TimeData> = _currentTime.asStateFlow()

    private val _currentDate = MutableStateFlow(getDateUseCase())
    /**
     * Observable state of the current date
     * Contains formatted date string
     */
    val currentDate: StateFlow<String> = _currentDate.asStateFlow()

    init {
        startTimeUpdate()
    }

    /**
     * Starts a coroutine that updates the time every second
     * The coroutine runs within the ViewModel's scope and will be automatically
     * cancelled when the ViewModel is cleared
     */
    private fun startTimeUpdate() {
        viewModelScope.launch {
            while (true) {
                delay(1000) // Wait for 1 second
                _currentTime.value = getTimeUseCase()
            }
        }
    }
} 