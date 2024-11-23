package com.example.simpleclock.domain.timer

import android.content.Context
import android.content.Intent
import com.example.simpleclock.service.TimerService

/**
 * Controller class that handles communication with TimerService
 * Prevents context leaks by using application context
 */
class TimerController(context: Context) {
    private val appContext = context.applicationContext

    /**
     * Starts the timer with specified duration
     * @param totalSeconds Total duration in seconds
     */
    fun startTimer(totalSeconds: Int) {
        Intent(appContext, TimerService::class.java).apply {
            action = TimerService.ACTION_START
            putExtra(TimerService.EXTRA_TIME, totalSeconds)
            appContext.startService(this)
        }
    }

    /**
     * Pauses the running timer
     */
    fun pauseTimer() {
        Intent(appContext, TimerService::class.java).apply {
            action = TimerService.ACTION_PAUSE
            appContext.startService(this)
        }
    }

    /**
     * Resets the timer
     */
    fun resetTimer() {
        Intent(appContext, TimerService::class.java).apply {
            action = TimerService.ACTION_RESET
            appContext.startService(this)
        }
    }
} 