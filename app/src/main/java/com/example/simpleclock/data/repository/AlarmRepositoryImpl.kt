package com.example.simpleclock.data.repository

import com.example.simpleclock.data.database.AlarmDao
import com.example.simpleclock.data.database.AlarmEntity
import com.example.simpleclock.domain.model.Alarm
import com.example.simpleclock.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek

class AlarmRepositoryImpl(
    private val alarmDao: AlarmDao
) : AlarmRepository {

    override fun getAlarms(): Flow<List<Alarm>> {
        return alarmDao.getAlarms().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addAlarm(alarm: Alarm) {
        alarmDao.insertAlarm(alarm.toEntity())
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        alarmDao.updateAlarm(alarm.toEntity())
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        alarmDao.deleteAlarm(alarm.toEntity())
    }

    override suspend fun toggleAlarm(alarm: Alarm) {
        updateAlarm(alarm.copy(isEnabled = !alarm.isEnabled))
    }

    private fun AlarmEntity.toDomain(): Alarm {
        return Alarm(
            id = id,
            hour = hour,
            minute = minute,
            isEnabled = isEnabled,
            label = label,
            repeatDays = repeatDays.split(",")
                .filter { it.isNotEmpty() }
                .map { DayOfWeek.valueOf(it) }
                .toSet(),
            vibrate = vibrate,
            soundUri = soundUri
        )
    }

    private fun Alarm.toEntity(): AlarmEntity {
        return AlarmEntity(
            id = id,
            hour = hour,
            minute = minute,
            isEnabled = isEnabled,
            label = label,
            repeatDays = repeatDays.joinToString(",") { it.name },
            vibrate = vibrate,
            soundUri = soundUri
        )
    }
} 