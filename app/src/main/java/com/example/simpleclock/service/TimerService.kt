package com.example.simpleclock.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.*

/**
 * Foreground service that handles timer functionality
 * Features:
 * - Maintains timer state even when app is in background
 * - Shows persistent notification with timer controls
 * - Broadcasts timer updates to the app
 */
class TimerService : Service() {
    companion object {
        const val ACTION_START = "START"
        const val ACTION_PAUSE = "PAUSE"
        const val ACTION_RESET = "RESET"
        const val ACTION_UPDATE = "com.example.simpleclock.ACTION_TIMER_UPDATE"
        const val ACTION_STATE_CHANGE = "com.example.simpleclock.ACTION_STATE_CHANGE"
        const val EXTRA_TIME = "EXTRA_TIME"
        const val EXTRA_IS_RUNNING = "EXTRA_IS_RUNNING"
    }

    private val serviceScope = CoroutineScope(Dispatchers.Default + Job())
    private var timerJob: Job? = null
    private var timeLeft = 0
    private var isRunning = false
    private lateinit var notificationHelper: TimerNotificationHelper

    override fun onCreate() {
        super.onCreate()
        notificationHelper = TimerNotificationHelper(this)
        notificationHelper.createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sendStateBroadcast(isRunning)
        
        when (intent?.action) {
            ACTION_START -> handleStartAction(intent)
            ACTION_PAUSE -> pauseTimer()
            ACTION_RESET -> resetTimer()
        }
        return START_STICKY
    }

    /**
     * Starts or resumes the timer with the specified duration
     */
    private fun handleStartAction(intent: Intent) {
        if (!isRunning) {
            val totalSeconds = intent.getIntExtra(EXTRA_TIME, timeLeft)
            startTimer(totalSeconds)
        }
    }

    /**
     * Starts the timer countdown and updates notification
     */
    private fun startTimer(totalSeconds: Int) {
        timeLeft = totalSeconds
        isRunning = true
        
        timerJob?.cancel()
        timerJob = serviceScope.launch {
            val notification = notificationHelper.createNotificationBuilder(true).build()
            startForeground(TimerNotificationHelper.NOTIFICATION_ID, notification)
            
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
                notificationHelper.updateNotification(timeLeft)
                sendUpdateBroadcast()
            }
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
        sendStateBroadcast(true)
    }

    /**
     * Pauses the current timer
     */
    private fun pauseTimer() {
        timerJob?.cancel()
        isRunning = false
        notificationHelper.createNotificationBuilder(false)
        notificationHelper.updateNotification(timeLeft)
        sendStateBroadcast(false)
    }

    /**
     * Resets the timer and stops the service
     */
    private fun resetTimer() {
        timerJob?.cancel()
        timeLeft = 0
        isRunning = false
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        sendStateBroadcast(false)
    }

    private fun sendUpdateBroadcast() {
        sendBroadcast(Intent(ACTION_UPDATE).apply {
            putExtra(EXTRA_TIME, timeLeft)
        })
    }

    private fun sendStateBroadcast(isRunning: Boolean) {
        sendBroadcast(Intent(ACTION_STATE_CHANGE).apply {
            putExtra(EXTRA_TIME, timeLeft)
            putExtra(EXTRA_IS_RUNNING, isRunning)
        })
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
} 