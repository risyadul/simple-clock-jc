package com.example.simpleclock.presentation.screen.timer

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simpleclock.presentation.screen.timer.components.TimerButtons
import com.example.simpleclock.presentation.screen.timer.components.TimerDisplay
import com.example.simpleclock.presentation.screen.timer.components.TimerPicker

/**
 * Main screen composable for the timer feature
 * Shows either a time picker or running timer display based on timer state
 *
 * Layout structure:
 * ```
 * [Timer Picker/Display]
 *        Spacer
 * [Control Buttons    ]
 * ```
 *
 * Features:
 * - Switches between picker and display based on timer state
 * - Centered layout with proper spacing
 * - Control buttons for timer operations
 *
 * @param viewModel ViewModel that manages timer state and operations
 */
@Composable
fun TimerScreen(viewModel: TimerViewModel) {
    val timerState by viewModel.timerState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!timerState.isRunning) {
            TimerPicker(
                hours = timerState.hours,
                minutes = timerState.minutes,
                seconds = timerState.seconds,
                onTimeSet = { h, m, s -> viewModel.setTime(h, m, s) }
            )
        } else {
            TimerDisplay(timerState)
        }

        Spacer(modifier = Modifier.height(32.dp))

        TimerButtons(
            isRunning = timerState.isRunning,
            totalSeconds = timerState.totalSeconds,
            onStartPause = {
                if (timerState.isRunning) viewModel.pauseTimer()
                else if (timerState.totalSeconds > 0) viewModel.startTimer()
            },
            onReset = { viewModel.resetTimer() }
        )
    }
} 