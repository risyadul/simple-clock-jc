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

class ClockViewModel(
    private val getTimeUseCase: GetTimeUseCase,
    getDateUseCase: GetDateUseCase
) : ViewModel() {
    private val _currentTime = MutableStateFlow(getTimeUseCase())
    val currentTime: StateFlow<TimeData> = _currentTime.asStateFlow()

    private val _currentDate = MutableStateFlow(getDateUseCase())
    val currentDate: StateFlow<String> = _currentDate.asStateFlow()

    init {
        startTimeUpdate()
    }

    private fun startTimeUpdate() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _currentTime.value = getTimeUseCase()
            }
        }
    }
} 