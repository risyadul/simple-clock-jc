package com.example.simpleclock.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.simpleclock.MainActivity
import com.example.simpleclock.R
import kotlinx.coroutines.*

class TimerService : Service() {
    companion object {
        const val CHANNEL_ID = "TimerChannel"
        const val NOTIFICATION_ID = 1
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
    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sendStateBroadcast(isRunning)
        
        when (intent?.action) {
            ACTION_START -> {
                if (!isRunning) {
                    val totalSeconds = intent.getIntExtra(EXTRA_TIME, timeLeft)
                    startTimer(totalSeconds)
                }
            }
            ACTION_PAUSE -> {
                pauseTimer()
            }
            ACTION_RESET -> {
                resetTimer()
            }
        }
        return START_STICKY
    }

    private fun startTimer(totalSeconds: Int) {
        timeLeft = totalSeconds
        isRunning = true
        notificationBuilder = createNotificationBuilder()
        
        timerJob?.cancel()
        timerJob = serviceScope.launch {
            startForeground(NOTIFICATION_ID, notificationBuilder.build())
            
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
                updateNotificationSilently()
                sendBroadcast(Intent(ACTION_UPDATE).apply {
                    putExtra(EXTRA_TIME, timeLeft)
                })
            }
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
        sendStateBroadcast(true)
    }

    private fun pauseTimer() {
        timerJob?.cancel()
        isRunning = false
        notificationBuilder = createNotificationBuilder() // Recreate with new action
        updateNotificationSilently()
        sendStateBroadcast(false)
    }

    private fun resetTimer() {
        timerJob?.cancel()
        timeLeft = 0
        isRunning = false
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        sendStateBroadcast(false)
    }

    private fun createNotificationBuilder(): NotificationCompat.Builder {
        val notificationIntent = Intent(this, MainActivity::class.java).apply {
            putExtra(MainActivity.EXTRA_SHOW_TIMER, true)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Create action based on current state
        val actionIntent = Intent(this, TimerService::class.java).apply {
            action = if (isRunning) ACTION_PAUSE else ACTION_START
        }
        val actionPendingIntent = PendingIntent.getService(
            this, 1, actionIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val resetIntent = Intent(this, TimerService::class.java).apply {
            action = ACTION_RESET
        }
        val resetPendingIntent = PendingIntent.getService(
            this, 2, resetIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Timer")
            .setSmallIcon(R.drawable.ic_timer)
            .setContentIntent(pendingIntent)
            .addAction(
                if (isRunning) R.drawable.ic_pause else R.drawable.ic_timer,
                if (isRunning) "Pause" else "Start",
                actionPendingIntent
            )
            .addAction(R.drawable.ic_reset, "Reset", resetPendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .setOnlyAlertOnce(true)
    }

    private fun updateNotificationSilently() {
        val hours = timeLeft / 3600
        val minutes = (timeLeft % 3600) / 60
        val seconds = timeLeft % 60
        val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        notificationBuilder.setContentText(timeString)
        
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Timer Notifications",
                NotificationManager.IMPORTANCE_LOW // Using LOW importance to prevent sound
            ).apply {
                description = "Shows timer notifications"
                setSound(null, null) // Explicitly disable sound
                enableLights(false)
                enableVibration(false)
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
} 