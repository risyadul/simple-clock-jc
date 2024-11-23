package com.example.simpleclock.presentation.screen.clock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.simpleclock.presentation.screen.clock.components.DateDisplay
import com.example.simpleclock.presentation.screen.clock.components.TimeDisplay

@Composable
fun ClockScreen(viewModel: ClockViewModel) {
    val currentTime by viewModel.currentTime.collectAsState()
    val currentDate by viewModel.currentDate.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2B2D42),
                        Color(0xFF1A1B2E)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DateDisplay(date = currentDate)
            Spacer(modifier = Modifier.height(32.dp))
            TimeDisplay(time = currentTime)
        }
    }
} 