package com.example.simpleclock.presentation.screen.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleclock.domain.model.Alarm
import com.example.simpleclock.domain.model.AlarmState
import com.example.simpleclock.domain.repository.AlarmRepository
import com.example.simpleclock.domain.usecase.ScheduleAlarmUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek

class AlarmViewModel(
    private val repository: AlarmRepository,
    private val scheduleAlarmUseCase: ScheduleAlarmUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AlarmState())
    val state: StateFlow<AlarmState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAlarms().collect { alarms ->
                _state.value = _state.value.copy(alarms = alarms)
            }
        }
    }

    fun addAlarm(hour: Int, minute: Int, repeatDays: Set<DayOfWeek> = emptySet()) {
        viewModelScope.launch {
            val alarm = Alarm(
                hour = hour,
                minute = minute,
                repeatDays = repeatDays
            )
            repository.addAlarm(alarm)
            scheduleAlarmUseCase(alarm)
        }
    }

    fun toggleAlarm(alarm: Alarm) {
        viewModelScope.launch {
            repository.toggleAlarm(alarm)
            if (!alarm.isEnabled) {
                scheduleAlarmUseCase.cancel(alarm.id)
            } else {
                scheduleAlarmUseCase(alarm)
            }
        }
    }

    fun deleteAlarm(alarm: Alarm) {
        viewModelScope.launch {
            repository.deleteAlarm(alarm)
            scheduleAlarmUseCase.cancel(alarm.id)
        }
    }

    fun checkAlarmPermission(): Boolean {
        return scheduleAlarmUseCase.hasAlarmPermission()
    }

    fun openAlarmSettings() {
        // This should be handled by the UI layer or a separate use case
        // as it requires Context to start an Activity
    }
} 