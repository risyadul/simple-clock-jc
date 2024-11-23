package com.example.simpleclock.presentation.screen.timer.components

import NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TimerPicker(
    hours: Int,
    minutes: Int,
    seconds: Int,
    onTimeSet: (Int, Int, Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = Color(0xFF3A3D50).copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 16.dp, horizontal = 32.dp)
    ) {
        NumberPicker(
            value = hours,
            onValueChange = { onTimeSet(it, minutes, seconds) },
            range = 0..23,
            label = "h"
        )
        NumberPicker(
            value = minutes,
            onValueChange = { onTimeSet(hours, it, seconds) },
            range = 0..59,
            label = "m"
        )
        NumberPicker(
            value = seconds,
            onValueChange = { onTimeSet(hours, minutes, it) },
            range = 0..59,
            label = "s"
        )
    }
} 