package com.example.simpleclock.domain.usecase

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.simpleclock.domain.model.Alarm
import com.example.simpleclock.service.AlarmService
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar

class ScheduleAlarmUseCase(
    private val context: Context,
    private val alarmManager: AlarmManager
) {
    operator fun invoke(alarm: Alarm) {
        if (!hasAlarmPermission()) {
            // Handle permission not granted
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scheduleAlarmApi26(alarm)
        } else {
            scheduleAlarmLegacy(alarm)
        }
    }

    fun hasAlarmPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleAlarmApi26(alarm: Alarm) {
        val now = LocalDateTime.now()
        var alarmTime = LocalDateTime.now()
            .withHour(alarm.hour)
            .withMinute(alarm.minute)
            .withSecond(0)
            .withNano(0)

        if (alarmTime.isBefore(now) && alarm.repeatDays.isEmpty()) {
            alarmTime = alarmTime.plusDays(1)
        }

        val intent = createAlarmIntent(alarm)
        val pendingIntent = createPendingIntent(alarm.id, intent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            // Fallback to inexact alarm if permission not granted
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                pendingIntent
            )
        } else {
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                    alarmTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    pendingIntent
                ),
                pendingIntent
            )
        }
    }

    private fun scheduleAlarmLegacy(alarm: Alarm) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, alarm.hour)
            set(Calendar.MINUTE, alarm.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (calendar.timeInMillis <= System.currentTimeMillis() && alarm.repeatDays.isEmpty()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val intent = createAlarmIntent(alarm)
        val pendingIntent = createPendingIntent(alarm.id, intent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                    calendar.timeInMillis,
                    pendingIntent
                ),
                pendingIntent
            )
        }
    }

    private fun createAlarmIntent(alarm: Alarm): Intent {
        return Intent(context, AlarmService::class.java).apply {
            putExtra(AlarmService.EXTRA_ALARM_ID, alarm.id)
        }
    }

    private fun createPendingIntent(alarmId: Int, intent: Intent): PendingIntent {
        val flags =
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        return PendingIntent.getService(
            context,
            alarmId,
            intent,
            flags
        )
    }

    fun cancel(alarmId: Int) {
        val intent = Intent(context, AlarmService::class.java)
        val flags =
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        val pendingIntent = PendingIntent.getService(
            context,
            alarmId,
            intent,
            flags
        )
        alarmManager.cancel(pendingIntent)
    }
} 