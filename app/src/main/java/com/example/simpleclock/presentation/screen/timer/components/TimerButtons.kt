package com.example.simpleclock.presentation.screen.timer.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TimerButtons(
    isRunning: Boolean,
    totalSeconds: Int,
    onStartPause: () -> Unit,
    onReset: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onStartPause,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3A3D50).copy(alpha = 0.3f),
                disabledContainerColor = Color(0xFF3A3D50).copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = isRunning || totalSeconds > 0
        ) {
            Text(
                if (isRunning) "Pause" else "Start",
                color = if (isRunning || totalSeconds > 0) 
                    Color.White else Color.White.copy(alpha = 0.5f),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        Button(
            onClick = onReset,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3A3D50).copy(alpha = 0.3f),
                disabledContainerColor = Color(0xFF3A3D50).copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = totalSeconds > 0 || isRunning
        ) {
            Text(
                "Reset",
                color = if (totalSeconds > 0 || isRunning) 
                    Color.White else Color.White.copy(alpha = 0.5f),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
} 