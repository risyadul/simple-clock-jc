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

/**
 * MainActivity is the main entry point of the application that handles:
 * - ViewModel initialization using Koin
 * - BroadcastReceiver registration for Timer
 * - UI setup using Jetpack Compose
 */
class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
        const val EXTRA_SHOW_TIMER = "EXTRA_SHOW_TIMER"
    }

    // ViewModel injection using Koin
    private val clockViewModel: ClockViewModel by viewModel()
    private val timerViewModel: TimerViewModel by viewModel { parametersOf(applicationContext) }

    // BroadcastReceiver to receive updates from TimerService
    private val timerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            handleTimerServiceUpdate(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTimerReceiver()
        initializeTimerService()
        setupUserInterface()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterTimerReceiver()
    }

    /**
     * Handles updates received from TimerService
     */
    private fun handleTimerServiceUpdate(intent: Intent?) {
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

    /**
     * Registers BroadcastReceiver to receive timer updates
     */
    private fun setupTimerReceiver() {
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
    }

    /**
     * Starts the TimerService
     */
    private fun initializeTimerService() {
        Intent(this, TimerService::class.java).also { intent ->
            startService(intent)
        }
    }

    /**
     * Sets up the UI using Jetpack Compose
     */
    private fun setupUserInterface() {
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

    /**
     * Cleans up BroadcastReceiver when Activity is destroyed
     */
    private fun unregisterTimerReceiver() {
        try {
            unregisterReceiver(timerReceiver)
        } catch (e: Exception) {
            Log.e(TAG, "Error unregistering receiver: ${e.localizedMessage.orEmpty()}")
        }
    }
}