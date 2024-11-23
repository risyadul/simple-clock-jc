package com.example.simpleclock.presentation.screen.timer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpleclock.domain.model.TimerState

@Composable
fun TimerDisplay(state: TimerState) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    
    val fontSize = when {
        screenWidth < 360.dp -> 48.sp
        screenWidth < 480.dp -> 56.sp
        else -> 72.sp
    }
    
    val horizontalPadding = when {
        screenWidth < 360.dp -> 16.dp
        screenWidth < 480.dp -> 24.dp
        else -> 32.dp
    }

    Box(
        modifier = Modifier
            .background(
                color = Color(0xFF3A3D50).copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 24.dp, horizontal = horizontalPadding)
            .widthIn(max = screenWidth - 32.dp)
    ) {
        Text(
            text = String.format(
                "%02d:%02d:%02d",
                state.hours,
                state.minutes,
                state.seconds
            ),
            color = Color.White,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
} 