package com.example.simpleclock.presentation.screen.clock.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpleclock.domain.model.TimeData

/**
 * Composable that displays the current time in a stylized format
 * Features:
 * - Hours and minutes in large text
 * - Seconds and AM/PM indicator in smaller text
 * - Semi-transparent background with rounded corners
 * - Different text sizes and opacities for visual hierarchy
 *
 * Layout:
 * ```
 * [HH:MM] [SS]
 *         [AM/PM]
 * ```
 *
 * @param time TimeData object containing hours, minutes, seconds, and AM/PM indicator
 */
@Composable
fun TimeDisplay(time: TimeData) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = Color(0xFF3A3D50).copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 32.dp, vertical = 24.dp)
    ) {
        Text(
            text = "${time.hours}:${time.minutes}",
            color = Color.White,
            fontSize = 72.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = time.seconds,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 32.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                text = time.amPm,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
} 