package com.example.simpleclock.presentation.screen.timer

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simpleclock.presentation.screen.timer.components.TimerButtons
import com.example.simpleclock.presentation.screen.timer.components.TimerDisplay
import com.example.simpleclock.presentation.screen.timer.components.TimerPicker

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