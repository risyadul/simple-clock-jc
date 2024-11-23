package com.example.simpleclock.presentation.screen.timer

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.simpleclock.domain.model.TimerState
import com.example.simpleclock.service.TimerService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimerViewModel(private val context: Context) : ViewModel() {
    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    fun setTime(hours: Int, minutes: Int, seconds: Int) {
        _timerState.value = TimerState(hours = hours, minutes = minutes, seconds = seconds)
    }

    fun startTimer() {
        if (_timerState.value.totalSeconds > 0) {
            _timerState.value = _timerState.value.copy(isRunning = true)
            Intent(context, TimerService::class.java).apply {
                action = TimerService.ACTION_START
                putExtra(TimerService.EXTRA_TIME, _timerState.value.totalSeconds)
                context.startService(this)
            }
        }
    }

    fun pauseTimer() {
        Intent(context, TimerService::class.java).apply {
            action = TimerService.ACTION_PAUSE
            context.startService(this)
        }
        _timerState.value = _timerState.value.copy(isRunning = false)
    }

    fun resetTimer() {
        Intent(context, TimerService::class.java).apply {
            action = TimerService.ACTION_RESET
            context.startService(this)
        }
        _timerState.value = TimerState()
    }

    fun updateTime(totalSeconds: Int) {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        _timerState.value = _timerState.value.copy(
            hours = hours,
            minutes = minutes,
            seconds = seconds
        )
    }

    fun updateState(totalSeconds: Int, isRunning: Boolean) {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        _timerState.value = TimerState(
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            isRunning = isRunning
        )
    }
} 