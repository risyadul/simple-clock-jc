package com.example.simpleclock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.simpleclock.domain.repository.AlarmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

/**
 * BroadcastReceiver that restores alarms after device boot
 */
class BootReceiver : BroadcastReceiver() {
    private val repository: AlarmRepository by inject(AlarmRepository::class.java)
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            scope.launch {
                repository.getAlarms().collect { alarms ->
                    alarms.filter { it.isEnabled }.forEach { alarm ->
                        // Reschedule each enabled alarm
                        // You might want to create a use case for this
                        // and inject AlarmManager through dependency injection
                        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
                        // Schedule alarm using the same logic as in AlarmViewModel
                    }
                }
            }
        }
    }
} 