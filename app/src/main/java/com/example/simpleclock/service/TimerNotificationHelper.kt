package com.example.simpleclock.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.simpleclock.MainActivity
import com.example.simpleclock.R

/**
 * Helper class to handle all notification-related operations for the Timer
 */
class TimerNotificationHelper(private val context: Context) {
    companion object {
        const val CHANNEL_ID = "TimerChannel"
        const val NOTIFICATION_ID = 1
    }

    private lateinit var notificationBuilder: NotificationCompat.Builder

    /**
     * Creates and configures the notification channel for Android O and above
     */
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Timer Notifications",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows timer notifications"
                setSound(null, null)
                enableLights(false)
                enableVibration(false)
            }
            getNotificationManager().createNotificationChannel(channel)
        }
    }

    /**
     * Creates a notification builder with proper actions based on timer state
     * @param isRunning Current state of the timer
     * @return Configured NotificationCompat.Builder
     */
    fun createNotificationBuilder(isRunning: Boolean): NotificationCompat.Builder {
        val mainActivityIntent = createMainActivityIntent()
        val actionIntent = createActionIntent(isRunning)
        val resetIntent = createResetIntent()

        notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Timer")
            .setSmallIcon(R.drawable.ic_timer)
            .setContentIntent(mainActivityIntent)
            .addAction(
                if (isRunning) R.drawable.ic_pause else R.drawable.ic_timer,
                if (isRunning) "Pause" else "Start",
                actionIntent
            )
            .addAction(R.drawable.ic_reset, "Reset", resetIntent)
            .setOngoing(true)
            .setSilent(true)
            .setOnlyAlertOnce(true)

        return notificationBuilder
    }

    /**
     * Updates the notification with current time without making sound
     * @param timeLeft Remaining time in seconds
     */
    fun updateNotification(timeLeft: Int) {
        val timeString = formatTime(timeLeft)
        notificationBuilder.setContentText(timeString)
        getNotificationManager().notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createMainActivityIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra(MainActivity.EXTRA_SHOW_TIMER, true)
        }
        return PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createActionIntent(isRunning: Boolean): PendingIntent {
        val intent = Intent(context, TimerService::class.java).apply {
            action = if (isRunning) TimerService.ACTION_PAUSE else TimerService.ACTION_START
        }
        return PendingIntent.getService(
            context, 1, intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createResetIntent(): PendingIntent {
        val intent = Intent(context, TimerService::class.java).apply {
            action = TimerService.ACTION_RESET
        }
        return PendingIntent.getService(
            context, 2, intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun formatTime(timeInSeconds: Int): String {
        val hours = timeInSeconds / 3600
        val minutes = (timeInSeconds % 3600) / 60
        val seconds = timeInSeconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun getNotificationManager(): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
} 