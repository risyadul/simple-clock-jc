package com.example.simpleclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.simpleclock.presentation.navigation.BottomNavItem
import com.example.simpleclock.presentation.screen.main.MainScreen
import com.example.simpleclock.presentation.screen.clock.ClockViewModel
import com.example.simpleclock.presentation.screen.timer.TimerViewModel
import com.example.simpleclock.service.TimerService
import com.example.simpleclock.ui.theme.MyApplicationTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    companion object {
        const val EXTRA_SHOW_TIMER = "EXTRA_SHOW_TIMER"
    }

    private val clockViewModel: ClockViewModel by viewModel()
    private val timerViewModel: TimerViewModel by viewModel { parametersOf(applicationContext) }
    private val timerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                TimerService.ACTION_UPDATE -> {
                    val timeLeft = intent.getIntExtra(TimerService.EXTRA_TIME, 0)
                    timerViewModel.updateTime(timeLeft)
                }
                TimerService.ACTION_STATE_CHANGE -> {
                    val timeLeft = intent.getIntExtra(TimerService.EXTRA_TIME, 0)
                    val isRunning = intent.getBooleanExtra(TimerService.EXTRA_IS_RUNNING, false)
                    timerViewModel.updateState(timeLeft, isRunning)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Register receiver
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(
                timerReceiver,
                IntentFilter().apply {
                    addAction(TimerService.ACTION_UPDATE)
                    addAction(TimerService.ACTION_STATE_CHANGE)
                },
                Context.RECEIVER_NOT_EXPORTED
            )
        }

        // Request current state from service
        Intent(this, TimerService::class.java).also { intent ->
            startService(intent)
        }

        val showTimer = intent?.getBooleanExtra(EXTRA_SHOW_TIMER, false) ?: false

        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainScreen(
                    clockViewModel = clockViewModel,
                    timerViewModel = timerViewModel,
                    initialTab = if (showTimer) BottomNavItem.Timer else BottomNavItem.Clock
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(timerReceiver)
        } catch (e: Exception) {
            Log.e(MainActivity::class.java.simpleName, e.localizedMessage.orEmpty())
        }
    }
}