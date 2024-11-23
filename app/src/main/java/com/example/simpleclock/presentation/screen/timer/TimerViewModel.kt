package com.example.simpleclock.presentation.screen.timer

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.simpleclock.domain.model.TimerState
import com.example.simpleclock.domain.timer.TimerController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel that manages the timer functionality and state
 *
 * This ViewModel is responsible for:
 * - Managing timer state using StateFlow
 * - Communicating with TimerService through TimerController
 * - Handling timer controls (start, pause, reset)
 * - Processing timer updates from the service
 */
class TimerViewModel(context: Context) : ViewModel() {
    private val _timerState = MutableStateFlow(TimerState())
    private val timerController = TimerController(context)
    
    /**
     * Observable state of the timer containing hours, minutes, seconds and running status
     */
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    /**
     * Sets the timer duration
     *
     * @param hours Number of hours
     * @param minutes Number of minutes
     * @param seconds Number of seconds
     */
    fun setTime(hours: Int, minutes: Int, seconds: Int) {
        _timerState.value = TimerState(hours = hours, minutes = minutes, seconds = seconds)
    }

    /**
     * Starts or resumes the timer if there is time remaining
     * Delegates to TimerController for service communication
     */
    fun startTimer() {
        if (_timerState.value.totalSeconds > 0) {
            _timerState.value = _timerState.value.copy(isRunning = true)
            timerController.startTimer(_timerState.value.totalSeconds)
        }
    }

    /**
     * Pauses the running timer
     * Delegates to TimerController for service communication
     */
    fun pauseTimer() {
        timerController.pauseTimer()
        _timerState.value = _timerState.value.copy(isRunning = false)
    }

    /**
     * Resets the timer to initial state
     * Delegates to TimerController for service communication
     */
    fun resetTimer() {
        timerController.resetTimer()
        _timerState.value = TimerState()
    }

    /**
     * Updates the timer state with new time value
     * Called when receiving updates from TimerService
     *
     * @param totalSeconds Total remaining seconds
     */
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

    /**
     * Updates both time and running state of the timer
     * Called when receiving state changes from TimerService
     *
     * @param totalSeconds Total remaining seconds
     * @param isRunning Whether the timer is currently running
     */
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